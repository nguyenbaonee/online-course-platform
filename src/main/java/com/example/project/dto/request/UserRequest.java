package com.example.project.dto.request;

import com.example.project.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    String username;
    @NotBlank @Min(6)
    String password;
    @Email
    String email;
    Role role;
}
