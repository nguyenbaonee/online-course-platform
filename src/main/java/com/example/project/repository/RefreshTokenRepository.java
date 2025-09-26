package com.example.project.repository;

import com.example.project.entity.RefreshToken;
import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteByExpiryDateBefore(Instant now);

    Optional<RefreshToken> findByToken(String token);

    boolean existsByToken(String token);
}


