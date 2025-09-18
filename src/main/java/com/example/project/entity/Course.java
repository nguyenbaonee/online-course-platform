package com.example.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name ="course")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name ="title", unique=true)
    String title;
    String description;
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "thumbnail_url")
    String thumbnailUrl;
    @Column(name = "video_url")
    String videoUrl;
    @OneToMany(mappedBy = "course")
    List<OrderItem> orderItemList;
    @OneToMany(mappedBy = "course")
    List<Enrollment> enrollmentList;
}
