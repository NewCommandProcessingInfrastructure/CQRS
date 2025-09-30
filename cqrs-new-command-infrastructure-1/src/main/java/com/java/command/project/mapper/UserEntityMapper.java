package com.java.command.project.mapper;

import com.java.command.project.entity.UserEntity;
import com.java.command.project.entity.UserRequest;
import com.java.command.project.entity.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

  // Request -> Entity
  UserEntity toEntity(UserRequest request);

  // Entity -> Response
  UserResponse toResponse(UserEntity entity);
}
