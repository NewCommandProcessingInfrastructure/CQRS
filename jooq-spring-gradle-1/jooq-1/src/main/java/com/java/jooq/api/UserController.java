package com.java.jooq.api;

import com.java.jooq.api.service.UserService;
import com.java.jooq.data.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @PostMapping(path = "/save-user")
  public ResponseEntity<String> saveNewUser(@RequestBody UserDTO user) {

    Long result = service.saveNewUser(user);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("New User Saved with ID: " + result);
  }
}
