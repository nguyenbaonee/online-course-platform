package com.example.project.service.impl;

import com.example.project.dto.response.EnrollmentRespose;
import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import com.example.project.entity.User;
import com.example.project.mapper.EnrollmentMapper;
import com.example.project.repository.EnrollmentRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.itf.EnrollmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EnrollmentServiceImpl implements EnrollmentService {
    EnrollmentRepository enrollmentRepository;
    UserRepository userRepository;
    EnrollmentMapper enrollmentMapper;
    @Override
    public void createEnrollment(Course course, String userId) {
    boolean exists = enrollmentRepository.existsByUser_idAndCourse_Id(userId,course.getId());
    if(exists){
        return;
    }
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    Enrollment enrollment = new Enrollment();
    enrollment.setCourse(course);
    enrollment.setUser(user);
    enrollmentRepository.save(enrollment);
    }
    @Override
    public EnrollmentRespose getEnrollment(String id) {
        Enrollment enrollment= enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return enrollmentMapper.toEnrollmentRespose(enrollment);
    }
}
