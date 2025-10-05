package com.java.workflow.app.mapper;

import com.java.workflow.app.data.UserRequest;
import com.java.workflow.app.data.UserResponse;
import com.java.workflow.app.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

  // Request -> Entity
  UserEntity toEntity(UserRequest request);

  // Entity -> Response
  UserResponse toResponse(UserEntity entity);
}
