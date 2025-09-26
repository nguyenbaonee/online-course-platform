package com.example.project.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    String title;
    String description;
    private BigDecimal price;
    String thumbnailUrl;
    String videoUrl;
}
