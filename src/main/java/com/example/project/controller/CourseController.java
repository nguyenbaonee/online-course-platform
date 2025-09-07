package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.course.CourseRequest;
import com.example.project.dto.course.CourseResponseBase;
import com.example.project.dto.course.CourseResponsePrivate;
import com.example.project.dto.course.CourseResponsePublic;
import com.example.project.service.itf.CourseService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourseController {
    CourseService courseService;

    @GetMapping("/course")
    public ApiResponse<List<CourseResponsePublic>> getAllCourses(){
        return ApiResponse.<List<CourseResponsePublic>>builder()
                .result(courseService.getAllCourses())
                .message("get all courses success")
                .build();
    }

    @GetMapping("/user/{userId}/course")
    public ApiResponse<List<CourseResponsePrivate>> getAllCoursePrivate(@PathVariable String userId){
        return ApiResponse.<List<CourseResponsePrivate>>builder()
                .result(courseService.getAllCoursesPrivate(userId))
                .build();
    }

    @GetMapping("/course/{courseId}")
    public ApiResponse<CourseResponseBase> getCourseByIdPublic(@PathVariable String courseId){
        return ApiResponse.<CourseResponseBase>builder()
                .result(courseService.getCourseByIdPublic(courseId))
                .build();
    }

    @GetMapping("user/{userId}/course/{courseId}")
    public ApiResponse<CourseResponseBase> getCourseByIdPrivate(@PathVariable String courseId,@PathVariable String userId){
        return ApiResponse.<CourseResponseBase>builder()
                .result(courseService.getCourseByIdPrivate(courseId,userId))
                .build();
    }

    @PostMapping("/course")
    public ApiResponse<Void> saveCourse(@RequestBody CourseRequest courseRequest){
        courseService.saveCourse(courseRequest);
        return ApiResponse.<Void>builder()
                .message("save course")
                .build();
    }

    @PutMapping("/course/{courseId}")
    public ApiResponse<Void> updateCourse(@PathVariable String courseId, @RequestBody CourseRequest courseRequest){
        courseService.updateCourse(courseId, courseRequest);
        return ApiResponse.<Void>builder()
                .message("update course")
                .build();
    }

    @DeleteMapping("/course/{courseId}")
    public ApiResponse<Void> deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
        return ApiResponse.<Void>builder()
                .message("delete course")
                .build();
    }
}
