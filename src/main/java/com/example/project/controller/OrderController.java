package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.request.OrderRequest;
import com.example.project.dto.response.OrderResponse;
import com.example.project.service.itf.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping("/orders")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(orderRequest))
                .message("Order created")
                .build();
    }

    @GetMapping("/oders")
    public ApiResponse<List<OrderResponse>> getOrders(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrders())
                .build();
    }

    @GetMapping("/order/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable String orderId){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrder(orderId))
                .build();
    }

    @GetMapping("/order/userId")
    public ApiResponse<List<OrderResponse>> getAllOrdersByUser (@RequestParam String userId){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrdersByUser(userId))
                .build();
    }
}
