package com.example.project.service.itf;

import com.example.project.entity.RefreshToken;
import com.example.project.entity.User;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(User user);
    public boolean verifyExpiration(RefreshToken token);
    public void revokeToken(RefreshToken token);
}