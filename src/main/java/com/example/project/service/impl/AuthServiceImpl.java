package com.example.project.service.impl;

import com.example.project.dto.request.IntrospectRequest;
import com.example.project.dto.request.LogoutRequest;
import com.example.project.dto.response.IntrospectResponse;
import com.example.project.dto.request.LoginRequest;
import com.example.project.dto.response.LoginResponse;
import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.RegisterResponse;
import com.example.project.entity.InvalidatedToken;
import com.example.project.entity.RefreshToken;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.exception.ErrorCode;
import com.example.project.mapper.AuthMapper;
import com.example.project.mapper.AuthMapperImpl;
import com.example.project.repository.InvalidatedTokenRepository;
import com.example.project.repository.RefreshTokenRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.itf.AuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    AuthMapper authMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    RefreshTokenServiceImpl refreshTokenService;
    RefreshTokenRepository refreshTokenRepository;
    private final AuthMapperImpl authMapperImpl;

    @NonFinal
    @Value("${app.jwt.secret}")
    protected String secretKey;

    @NonFinal
    @Value("${jwt.access.expiration}")
    private long accessTokenDurationMs;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException(ErrorCode.USER_ALREADY_EXISTS.getMessage());
        }
        User user = authMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Default role STUDENT not found"));
        user.setRole(studentRole);
        userRepository.save(user);
        return authMapper.toResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException(ErrorCode.PASSWORD_INVALID.getMessage());
        }
        var token = generateToken(request);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        return new LoginResponse(token,refreshToken.getToken());
    }

    @Override
    public String generateToken(LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(request.getUsername())
                .issuer(request.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(accessTokenDurationMs, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope",buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create JWT object", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if (user.getRole() != null) {
            scopeJoiner.add(user.getRole().getName());
        }
        return scopeJoiner.toString();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        try{
            verifyToken(token);
        }
            catch(Exception e){
                return IntrospectResponse.builder()
                        .valid(false)
                        .build();
        }

        return IntrospectResponse.builder()
                .valid(true)
                .build();
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(secretKey.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationDate = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(!expirationDate.after(new Date()) || !verified){
            throw new RuntimeException(ErrorCode.UNAUTHENTICATED.getMessage());
        }
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException(ErrorCode.UNAUTHENTICATED.getMessage());
        }
        return signedJWT;
    }

    @Override
    public LoginResponse refreshToken(String requestToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.verifyExpiration(refreshToken);
        LoginRequest loginRequest = LoginRequest.builder()
                .username(refreshToken.getUser().getUsername())
                .build();
        String newAccessToken = generateToken(loginRequest);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(refreshToken.getUser());

        refreshTokenService.revokeToken(refreshToken);

        return new LoginResponse(newAccessToken, newRefreshToken.getToken());
    }

    @Override
    public Void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        SignedJWT jwt = verifyToken(logoutRequest.getToken());
        String jti = jwt.getJWTClaimsSet().getJWTID();
        Date expirationDate = jwt.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expirationTime(expirationDate)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);
        return null;
    }


}
