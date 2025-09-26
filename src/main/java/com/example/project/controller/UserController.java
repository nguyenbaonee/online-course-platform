package com.example.project.controller;

import com.example.project.dto.ApiResponse;
import com.example.project.dto.request.ChangeRole;
import com.example.project.dto.request.UserRequest;
import com.example.project.dto.response.UserResponse;
import com.example.project.service.itf.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping("/users/{userId}")
    @PreAuthorize("#userId == authentication.name or hasRole(ADMIN)")
    public ApiResponse<UserResponse> getUser(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @PutMapping("/users/{userId}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, userRequest))
                .build();
    }

    @DeleteMapping("/users/{userId}")
    public ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ApiResponse.<Void>builder()
                .message("User deleted")
                .build();
    }

    @PostMapping("/users/{userId}")
    public ApiResponse<UserResponse> changeUserRole(@PathVariable String userId,@RequestBody ChangeRole changeRole) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.changeUserRole(userId, changeRole))
                .build();
    }
}
