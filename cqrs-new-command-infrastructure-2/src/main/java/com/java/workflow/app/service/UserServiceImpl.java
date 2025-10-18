package com.java.workflow.app.service;

import com.java.workflow.app.data.UserRequest;
import com.java.workflow.app.data.UserResponse;
import com.java.workflow.app.entity.UserEntity;
import com.java.workflow.app.mapper.UserEntityMapper;
import com.java.workflow.app.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserEntityRepository repository;
  private final UserEntityMapper mapper;

  @Override
  public UserResponse getUserData(String username) {
    return repository.findByUsername(username);
  }

  @Override
  public UserResponse addUserToDatabase(UserRequest payload) {
    UserEntity entity = mapper.toEntity(payload);
    UserEntity entityResponse = repository.save(entity);
    log.info("Inside UserServiceImpl - After Repository Save");
    return mapper.toResponse(entityResponse);
  }
}
