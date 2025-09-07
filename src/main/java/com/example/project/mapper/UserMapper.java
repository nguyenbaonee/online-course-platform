package com.example.project.mapper;

import com.example.project.dto.usreDto.UserRequest;
import com.example.project.dto.usreDto.UserResponse;
import com.example.project.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role.name", target = "roleName")
    UserResponse toUserResponse(User user);
    List<UserResponse> toListUserResponse(List<User> users);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UserRequest userRequest, @MappingTarget User user);
}
