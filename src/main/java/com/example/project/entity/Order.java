package com.example.project.entity;

import com.example.project.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.project.enums.OrderStatus.PENDING;

@Entity
@Table(name ="orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(name ="total_price", precision = 10, scale = 2)
    BigDecimal totalPrice;

    @Column(name ="created_at")
    LocalDateTime createdTime;

    @Column(name ="updated_at")
    LocalDateTime updatedTime;

    @ManyToOne @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems;
    @PrePersist
    void createdTime(){
        this.createdTime = LocalDateTime.now();
    }
    @PreUpdate
    void updatedTime(){
        this.updatedTime = LocalDateTime.now();
    }
    public void addOrderItem(OrderItem orderItem){
        if(orderItems == null){
            orderItems = new ArrayList<>();
        }
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void removeOrderItem(OrderItem orderItem){
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }
    public BigDecimal sumPrice(){
        BigDecimal sum = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            sum = sum.add(orderItem.calculateSubtotal());
        }
        return sum;
    }
    public void recalcTotalPrice(){
        this.totalPrice = sumPrice();
    }
}

