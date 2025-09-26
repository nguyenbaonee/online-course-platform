package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.response.EnrollmentRespose;
import com.example.project.entity.Course;
import com.example.project.service.itf.EnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollmentController {
    EnrollmentService enrollmentService;

    @PostMapping("/enrollment/{userId}")
    public ApiResponse<Void> createEnrollment(@RequestBody Course course,@PathVariable String userId){
        enrollmentService.createEnrollment(course,userId);
        return ApiResponse.<Void>builder()
                .message("Enrollment created")
                .build();
    }

    @GetMapping("enrollment/{userId}")
    public ApiResponse<EnrollmentRespose> getEnrollment(@PathVariable String userId){
        return ApiResponse.<EnrollmentRespose>builder()
                .result(enrollmentService.getEnrollment(userId))
                .message("Enrollment found")
                .build();
    }
}
