package com.example.project.service.impl;

import com.example.project.dto.request.CourseRequest;
import com.example.project.dto.response.CourseResponseBase;
import com.example.project.dto.response.CourseResponsePrivate;
import com.example.project.dto.response.CourseResponsePublic;
import com.example.project.entity.Course;
import com.example.project.exception.ErrorCode;
import com.example.project.mapper.CourseMapper;
import com.example.project.repository.CourseRepository;
import com.example.project.repository.EnrollmentRepository;
import com.example.project.service.itf.CourseService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    EnrollmentRepository enrollmentRepository;
    CourseMapper courseMapper;

    @Override
    public List<CourseResponsePublic> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        return courseMapper.toCourseResponsePublicList(courseList);
    }

    @Override
    public List<CourseResponsePrivate> getAllCoursesPrivate(String userId) {
        if(!enrollmentRepository.existsByUser_Id(userId)) {
            throw new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage());
        }
        List<Course> courses = enrollmentRepository.findAllCoursesByUserId(userId);
        return courseMapper.toCourseResponsePPrivateList(courses);
    }

    @Override
    public CourseResponseBase getCourseByIdPublic(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.COURSE_NOTFOUND.getMessage()));
        return courseMapper.toCourseResponsePublic(course);
    }

    @Override
    public CourseResponseBase getCourseByIdPrivate(String courseId, String userId) {
        if(!enrollmentRepository.existsByUser_Id(userId)) {
            throw new RuntimeException(ErrorCode.USER_NOT_ENROLLED.getMessage());
        }
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException(ErrorCode.COURSE_NOTFOUND.getMessage()));
        return courseMapper.toCourseResponsePrivate(course);
    }

    @Override
    @Transactional
    public void saveCourse(CourseRequest courseRequest) {
        if(courseRepository.existsByTitle(courseRequest.getTitle()) ) {
            throw new RuntimeException(ErrorCode.TITLE_EXISTS.getMessage());
        }
        courseRepository.save(courseMapper.toCourse(courseRequest));
    }

    @Override
    @Transactional
    public void updateCourse(String id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.COURSE_NOTFOUND.getMessage()));
        courseMapper.updateCourse(courseRequest, course);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteCourse(String id) {
    courseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(ErrorCode.COURSE_NOTFOUND.getMessage()));
    courseRepository.deleteById(id);
    }
}
