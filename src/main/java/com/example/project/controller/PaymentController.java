package com.example.project.controller;

import com.example.project.service.impl.PaymentServiceImpl;
import com.example.project.service.itf.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl paymentServiceImpl;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<String> createPayment(@PathVariable String orderId) {
        String vnpayUrl = paymentServiceImpl.createPayment(orderId);
        return ResponseEntity.ok(vnpayUrl);
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnUrl(@RequestParam Map<String,String> allParams) {
        boolean success = paymentServiceImpl.verifyAndUpdate(allParams);
        if(success) return ResponseEntity.ok("Payment successful");
        return ResponseEntity.ok("Payment failed");
    }
}

