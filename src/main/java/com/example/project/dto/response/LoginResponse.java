package com.example.project.dto.response;

import com.example.project.entity.RefreshToken;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    String token;
    String refreshToken;
}
