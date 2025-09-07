package com.example.project.service.itf;

import com.example.project.dto.order.OrderRequest;
import com.example.project.dto.order.OrderResponse;
import com.example.project.entity.Course;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> getOrders();
    OrderResponse getOrder(String id);
    List<OrderResponse> getAllOrdersByUser(String userId);
}
