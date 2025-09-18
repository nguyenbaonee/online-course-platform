package com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name ="order_items",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id","course_id"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "order_id")
    Order order;
    @ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "course_id")
    Course course;
    public BigDecimal calculateSubtotal() {
        return (course == null || course.getPrice() == null)
        ? BigDecimal.ZERO : course.getPrice();
    }
}
