package com.example.project.repository;

import com.example.project.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    void deleteByExpirationTimeBefore(LocalDateTime time);
}
