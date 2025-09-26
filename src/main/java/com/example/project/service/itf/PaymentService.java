package com.example.project.service.itf;

import java.util.Map;

public interface PaymentService {
    String createPayment(String orderId);
    boolean verifyAndUpdate(Map<String, String> vnpParams);
    boolean checkHash(Map<String, String> params, String hash);
    String buildVnpayUrl(Map<String, String> params);
}
