package com.example.project.dto.auth;

import com.example.project.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank
    @Size(min = 6, max = 20, message = "USERNAME_INVALID")
    String username;
    @NotBlank @Size(min = 6, max = 20, message = "PASSWORD_INVALID")
    String password;
    @Email
    String email;
}
