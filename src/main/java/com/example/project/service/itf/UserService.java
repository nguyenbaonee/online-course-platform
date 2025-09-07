package com.example.project.service.itf;

import com.example.project.dto.role.ChangeRole;
import com.example.project.dto.usreDto.UserRequest;
import com.example.project.dto.usreDto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUser(String id);
    List<UserResponse> getAllUser();
    UserResponse updateUser(String id,UserRequest userRequest);
    void deleteUser(String id);
    UserResponse changeUserRole(String id, ChangeRole changeRole);
}
