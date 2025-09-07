package com.example.project.dto.course;

import com.example.project.entity.Enrollment;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponsePrivate implements CourseResponseBase {
    String id;
    String title;
    String description;
    private BigDecimal price;
    String thumbnailUrl;
    String videoUrl;
}
