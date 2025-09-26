package com.example.project.service.itf;

import com.example.project.dto.request.OrderRequest;
import com.example.project.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> getOrders();
    OrderResponse getOrder(String id);
    List<OrderResponse> getAllOrdersByUser(String userId);
}
