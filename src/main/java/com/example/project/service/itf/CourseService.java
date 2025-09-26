package com.example.project.service.itf;

import com.example.project.dto.request.CourseRequest;
import com.example.project.dto.response.CourseResponseBase;
import com.example.project.dto.response.CourseResponsePrivate;
import com.example.project.dto.response.CourseResponsePublic;

import java.util.List;

public interface CourseService {
    List<CourseResponsePublic> getAllCourses();
    List<CourseResponsePrivate> getAllCoursesPrivate(String userId);
    CourseResponseBase getCourseByIdPublic(String courseId);
    CourseResponseBase getCourseByIdPrivate(String courseId, String userId);
    void saveCourse(CourseRequest courseRequest);
    void updateCourse(String id,CourseRequest courseRequest);
    void deleteCourse(String id);
}
