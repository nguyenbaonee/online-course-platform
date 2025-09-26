package com.example.project.mapper;

import com.example.project.dto.response.EnrollmentRespose;
import com.example.project.entity.Enrollment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    EnrollmentRespose toEnrollmentRespose(Enrollment enrollment);
}
