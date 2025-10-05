package com.java.workflow.app.repository;

import com.java.workflow.app.data.UserResponse;
import com.java.workflow.app.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

  UserResponse findByUsername(String username);
}
