package com.example.project.service.itf;

import com.example.project.dto.IntrospectRequest;
import com.example.project.dto.IntrospectResponse;
import com.example.project.dto.auth.LoginRequest;
import com.example.project.dto.auth.LoginResponse;
import com.example.project.dto.auth.RegisterRequest;
import com.example.project.dto.auth.RegisterResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    String generateToken(LoginRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    LoginResponse refreshToken(String refreshToken);
}
