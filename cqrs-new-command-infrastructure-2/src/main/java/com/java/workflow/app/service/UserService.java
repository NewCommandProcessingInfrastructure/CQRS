package com.java.workflow.app.service;

import com.java.workflow.app.data.UserRequest;
import com.java.workflow.app.data.UserResponse;

public interface UserService {

  UserResponse getUserData(String username);

  UserResponse addUserToDatabase(UserRequest payload);
}
