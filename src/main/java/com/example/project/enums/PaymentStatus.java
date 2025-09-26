package com.example.project.enums;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public enum PaymentStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    FAILED("FAILED"),
    CANCELLED("CANCELLED"),
    REFUNDED("REFUNDED");

    private final String status;
}

