package com.example.project.service.impl;

import com.example.project.dto.role.ChangeRole;
import com.example.project.dto.usreDto.UserRequest;
import com.example.project.dto.usreDto.UserResponse;
import com.example.project.entity.Role;
import com.example.project.exception.ErrorCode;
import com.example.project.mapper.UserMapper;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import com.example.project.service.itf.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import com.example.project.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userMapper.toListUserResponse(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserResponse updateUser(String id,UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        userMapper.updateUser(userRequest,user);
        if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }


    @Override
    @Transactional
    public void deleteUser(String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserResponse changeUserRole(String id, ChangeRole changeRole) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.USER_NOTFOUND.getMessage()));
        Role role = roleRepository.findByName(changeRole.getRoleName())
                .orElseThrow(() -> new RuntimeException(ErrorCode.ROLE_NOTFOUND.getMessage()));
        user.setRole(role);
        return userMapper.toUserResponse(userRepository.save(user));
    }

}
