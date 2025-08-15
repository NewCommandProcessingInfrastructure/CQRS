package com.java.jooq.api.service;

import com.java.jooq.api.repository.UserRepository;
import com.java.jooq.data.UserDTO;
import com.java.jooq.entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Override
  public Long saveNewUser(UserDTO user) {

    UserData newUserData = UserData.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .age(user.getAge())
            .build();

    return repository.saveNewUser(newUserData);
  }
}
