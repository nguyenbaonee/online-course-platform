package com.example.project.dto.enrollment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentRespose {
    String enrollmentId;
    String courseId;
    String userId;
}
