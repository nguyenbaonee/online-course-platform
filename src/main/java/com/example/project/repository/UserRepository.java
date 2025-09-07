package com.example.project.repository;

import com.example.project.entity.User; // Đây mới là dòng đúng
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsById(String id);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(@NotBlank @Size(min = 6, max = 20, message = "USERNAME_INVALID") String username);
}