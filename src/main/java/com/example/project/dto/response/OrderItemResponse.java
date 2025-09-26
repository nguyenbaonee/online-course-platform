package com.example.project.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
   String orderItemId;
   BigDecimal orderItemPrice;
   String courseId;
   String courseTitle;
}
