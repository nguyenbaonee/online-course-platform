package com.example.project.entity;

import com.example.project.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // liên kết tới Order

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status; // PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED

    @Column(nullable = false, length = 50)
    private String provider; // VNPAY, MoMo, PayPal

    @Column(nullable = false, length = 100)
    private String txnRef; // mã giao dịch gửi lên VNPAY

    @Column(length = 100)
    private String transactionNo; // VNPAY trả về

    @Column(length = 50)
    private String bankCode;

    private LocalDateTime payDate; // ngày thanh toán từ VNPAY

    @Column(length = 10)
    private String responseCode; // vnp_ResponseCode

    @Column(length = 256)
    private String secureHash; // vnp_SecureHash

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
