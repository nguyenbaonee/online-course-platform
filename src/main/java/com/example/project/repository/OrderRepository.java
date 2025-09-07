package com.example.project.repository;

import com.example.project.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @EntityGraph(attributePaths = "orderItem")
    List<Order> findByUserId(String userId);
}
