package com.example.project.service.itf;

import com.example.project.dto.enrollment.EnrollmentRespose;
import com.example.project.entity.Course;

public interface EnrollmentService {
    void createEnrollment(Course course, String userId);
    EnrollmentRespose getEnrollment(String id);
}
