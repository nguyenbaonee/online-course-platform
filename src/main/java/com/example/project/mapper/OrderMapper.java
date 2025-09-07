package com.example.project.mapper;

import com.example.project.dto.order.OrderItemResponse;
import com.example.project.dto.order.OrderResponse;
import com.example.project.entity.Order;
import com.example.project.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "orderItems", target = "items")
    OrderResponse toOrderResponse(Order order);

    @Mapping(source = "id", target = "orderItemId")
//    @Mapping(expression = "java(item.calculateSubtotal())", target = "subtotal")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.title", target = "courseTitle")
    OrderItemResponse toOrderItemResponse(OrderItem item);
    List<OrderResponse> toOrderResponseList(List<Order> orders);
}

