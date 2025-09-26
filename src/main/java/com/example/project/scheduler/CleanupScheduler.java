package com.example.project.scheduler;

import com.example.project.repository.InvalidatedTokenRepository;
import com.example.project.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CleanupScheduler {

    private final InvalidatedTokenRepository tokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredTokens() {
        tokenRepository.deleteByExpirationTimeBefore(LocalDateTime.now());
        refreshTokenRepository.deleteByExpiryDateBefore(Instant.now());
    }

}

