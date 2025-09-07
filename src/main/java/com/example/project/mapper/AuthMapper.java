package com.example.project.mapper;

import com.example.project.dto.auth.RegisterRequest;
import com.example.project.dto.auth.RegisterResponse;
import com.example.project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toUser(RegisterRequest request);
    @Mapping(source="role.name",target = "role")
    RegisterResponse toResponse(User user);
}

