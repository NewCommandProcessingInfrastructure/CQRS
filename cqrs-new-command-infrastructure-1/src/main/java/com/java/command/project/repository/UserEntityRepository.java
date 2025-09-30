package com.java.command.project.repository;

import com.java.command.project.entity.UserEntity;
import com.java.command.project.entity.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

	UserResponse findByUsername(String username);
}
