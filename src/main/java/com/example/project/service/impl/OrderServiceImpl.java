package com.example.project.service.impl;

import com.example.project.dto.request.OrderRequest;
import com.example.project.dto.response.OrderResponse;
import com.example.project.entity.Course;
import com.example.project.entity.Order;
import com.example.project.entity.OrderItem;
import com.example.project.entity.User;
import com.example.project.exception.ErrorCode;
import com.example.project.mapper.OrderMapper;
import com.example.project.repository.CourseRepository;
import com.example.project.repository.OrderRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.itf.EnrollmentService;
import com.example.project.service.itf.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    CourseRepository courseRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;
    EnrollmentService enrollmentService;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        order.setUser(userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage())));
        for(String courseId : orderRequest.getCourseId()){
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException(ErrorCode.COURSE_NOTFOUND.getMessage()));
            OrderItem orderItem = new OrderItem();
            orderItem.setCourse(course);
            order.addOrderItem(orderItem);
            enrollmentService.createEnrollment(course, user.getId());
        }
        order.sumPrice();
        order.recalcTotalPrice();
        orderRepository.save(order);
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponseList = orders.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
        return orderResponseList;
    }

    @Override
    public OrderResponse getOrder(String id) {
        Order order= orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.ORDER_NOTFOUND.getMessage()));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrdersByUser(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toOrderResponseList(orders);
    }
}
