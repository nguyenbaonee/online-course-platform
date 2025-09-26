package com.example.project.dto.response;

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
