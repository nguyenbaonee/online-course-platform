package com.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank @Size(min = 6, max = 20, message = "USERNAME_INVALID")
    String username;
    @NotBlank @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    String password;
}
