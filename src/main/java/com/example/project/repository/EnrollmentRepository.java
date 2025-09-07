package com.example.project.repository;

import com.example.project.dto.course.CourseResponsePrivate;
import com.example.project.entity.Course;
import com.example.project.entity.Enrollment;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, String> {
    boolean existsByUser_Id(String userId);
    @Query("select e.course from Enrollment e where e.user.id = :userId ")
    List<Course> findAllCoursesByUserId(@Param("userId") String userId);
    boolean existsByUser_idAndCourse_Id(String userId, String id);
}
