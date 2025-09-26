package com.example.project.service.impl;

import com.example.project.configuration.VnpayConfig;
import com.example.project.entity.Order;
import com.example.project.entity.OrderItem;
import com.example.project.entity.Payment;
import com.example.project.enums.OrderStatus;
import com.example.project.enums.PaymentStatus;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URLEncoder;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final VnpayConfig vnpayConfig;
    private final EnrollmentServiceImpl enrollmentServiceImpl;

    public String createPayment(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Tạo payment PENDING
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setStatus(PaymentStatus.PENDING);
        String txnRef = UUID.randomUUID().toString();
        payment.setTxnRef(txnRef);

        paymentRepository.save(payment);

        // Sinh URL VNPAY
        Map<String, String> params = new HashMap<>();
        params.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        BigDecimal amount = order.getTotalPrice().multiply(BigDecimal.valueOf(100));
        params.put("vnp_Amount", amount.toBigInteger().toString());
        params.put("vnp_Command", "pay");
        params.put("vnp_OrderInfo", "Thanh toan order " + orderId);
        params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        params.put("vnp_TxnRef", txnRef);

        String queryUrl = buildVnpayUrl(params);
        return queryUrl;
    }

    public boolean verifyAndUpdate(Map<String, String> vnpParams) {
        String txnRef = vnpParams.get("vnp_TxnRef");
        String rspCode = vnpParams.get("vnp_ResponseCode");
        String secureHash = vnpParams.get("vnp_SecureHash");

        // Verify hash
        if (!checkHash(vnpParams, secureHash)) return false;

        Payment payment = paymentRepository.findByTxnRef(txnRef)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if ("00".equals(rspCode)) {
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.getOrder().setOrderStatus(OrderStatus.PAID);
            for (OrderItem item : payment.getOrder().getOrderItems()) {
                enrollmentServiceImpl.createEnrollment(item.getCourse(), payment.getOrder().getUser().getId());
            }
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.getOrder().setOrderStatus(OrderStatus.CANCELED);
        }


        paymentRepository.save(payment);
        orderRepository.save(payment.getOrder());

        return true;
    }

    private String buildVnpayUrl(Map<String, String> params) {
        try {
            List<String> fieldNames = new ArrayList<>(params.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();

            for (String fieldName : fieldNames) {
                String fieldValue = params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    String encodedValue = URLEncoder.encode(fieldValue, "UTF-8");
                    if (query.length() > 0) query.append("&");
                    query.append(fieldName).append("=").append(encodedValue);
                    if (hashData.length() > 0) hashData.append("&");
                    hashData.append(fieldName).append("=").append(fieldValue);
                }
            }

            String secureHash = hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());

            query.append("&vnp_SecureHash=").append(secureHash);

            return vnpayConfig.getPayUrl() + "?" + query.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error building VNPAY URL", e);
        }
    }

    private boolean checkHash(Map<String, String> params, String hash) {
        try {
            Map<String, String> filtered = new HashMap<>(params);
            filtered.remove("vnp_SecureHash");
            filtered.remove("vnp_SecureHashType"); // nếu có

            List<String> fieldNames = new ArrayList<>(filtered.keySet());
            Collections.sort(fieldNames);

            StringBuilder hashData = new StringBuilder();
            for (String fieldName : fieldNames) {
                if (hashData.length() > 0) hashData.append("&");
                hashData.append(fieldName).append("=").append(filtered.get(fieldName));
            }

            String check = hmacSHA512(vnpayConfig.getSecretKey(), hashData.toString());

            return hash.equals(check);
        } catch (Exception e) {
            return false;
        }
    }


    private String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA512");
            javax.crypto.spec.SecretKeySpec secretKeySpec = new javax.crypto.spec.SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC SHA512", e);
        }
    }

}

