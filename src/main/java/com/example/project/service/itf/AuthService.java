package com.example.project.service.itf;

import com.example.project.dto.request.IntrospectRequest;
import com.example.project.dto.request.LogoutRequest;
import com.example.project.dto.response.IntrospectResponse;
import com.example.project.dto.request.LoginRequest;
import com.example.project.dto.response.LoginResponse;
import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.RegisterResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    String generateToken(LoginRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    LoginResponse refreshToken(String refreshToken);
    Void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
}
