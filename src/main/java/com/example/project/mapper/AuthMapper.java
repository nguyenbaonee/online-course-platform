package com.example.project.mapper;

import com.example.project.dto.request.RegisterRequest;
import com.example.project.dto.response.RegisterResponse;
import com.example.project.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    User toUser(RegisterRequest request);
    @Mapping(source="role.name",target = "role")
    RegisterResponse toResponse(User user);
}

