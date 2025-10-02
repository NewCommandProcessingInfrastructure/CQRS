package com.java.command.project.service;

import com.java.command.project.entity.UserEntity;
import com.java.command.project.entity.UserRequest;
import com.java.command.project.entity.UserResponse;
import com.java.command.project.mapper.UserEntityMapper;
import com.java.command.project.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

	private final UserEntityRepository repository;
	private final UserEntityMapper mapper;

	@Override
	public UserResponse getUserData(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public UserResponse addUserToDatabase(UserRequest payload) {
//		log.info("Inside UserEntityServiceImpl - payload");
		UserEntity entity = mapper.toEntity(payload);
//		log.info("Inside UserEntityServiceImpl - after entity mapping");
		UserEntity entityResponse = repository.save(entity);
//		log.info("Inside UserEntityServiceImpl - after database save");
		UserResponse response = mapper.toResponse(entityResponse);
//		log.info("Inside UserEntityServiceImpl - after response mapping");
		return response;
	}
}
