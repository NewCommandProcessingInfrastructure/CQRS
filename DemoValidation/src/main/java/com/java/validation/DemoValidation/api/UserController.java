package com.java.validation.DemoValidation.api;

import com.java.validation.DemoValidation.data.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @PostMapping
  public ResponseEntity<String> createUser(@Valid @RequestBody UserRequest userRequest) {
    return ResponseEntity.status(HttpStatus.OK).body("User is valid!");
  }
}
