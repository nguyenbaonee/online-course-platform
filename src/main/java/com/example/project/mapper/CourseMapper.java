package com.example.project.mapper;

import com.example.project.dto.course.CourseRequest;
import com.example.project.dto.course.CourseResponsePrivate;
import com.example.project.dto.course.CourseResponsePublic;
import com.example.project.entity.Course;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toCourse(CourseRequest courseRequest);
    CourseResponsePublic toCourseResponsePublic(Course course);
    CourseResponsePrivate toCourseResponsePrivate(Course course);
    List<CourseResponsePublic> toCourseResponsePublicList(List<Course> courseList);
    List<CourseResponsePrivate> toCourseResponsePPrivateList(List<Course> courseList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourse(CourseRequest courseRequest, @MappingTarget Course course);
}
