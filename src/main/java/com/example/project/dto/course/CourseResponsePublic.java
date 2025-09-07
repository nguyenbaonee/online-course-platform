package com.example.project.dto.course;

import com.example.project.service.itf.CourseService;
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


