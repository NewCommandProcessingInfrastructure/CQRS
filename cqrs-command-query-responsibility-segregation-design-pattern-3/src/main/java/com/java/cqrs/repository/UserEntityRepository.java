package com.java.cqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java.cqrs.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

}
