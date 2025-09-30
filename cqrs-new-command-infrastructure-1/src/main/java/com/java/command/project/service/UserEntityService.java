package com.java.command.project.service;

import com.java.command.project.entity.UserRequest;
import com.java.command.project.entity.UserResponse;

public interface UserEntityService {

	UserResponse getUserData(String username);

	UserResponse addUserToDatabase(UserRequest payload);
}
