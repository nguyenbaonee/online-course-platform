package com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="enrollment",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","course_id"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(fetch = FetchType.LAZY)@JoinColumn(name = "course_id")
    Course course;
    LocalDateTime enrolledAt;
    @PrePersist
    void createdAt() {
        enrolledAt = LocalDateTime.now();
    }
}
