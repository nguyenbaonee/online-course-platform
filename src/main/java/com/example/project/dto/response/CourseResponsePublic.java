package com.example.project.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponsePublic implements CourseResponseBase {
    String id;
    String title;
    String description;
    private BigDecimal price;
    String thumbnailUrl;
}


