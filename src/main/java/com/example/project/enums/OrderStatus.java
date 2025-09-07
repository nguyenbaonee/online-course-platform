package com.example.project.enums;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public enum OrderStatus {
    PENDING("PENDING"),
    PAID("PAID"),
    FAILED("FAILED"),
    CANCELED("CANCELED");
    String status;
}
