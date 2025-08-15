package com.java.jooq.service;

import com.java.jooq.data.UserDTO;

public interface UserService {
  Long saveNewUser(UserDTO user);
}
