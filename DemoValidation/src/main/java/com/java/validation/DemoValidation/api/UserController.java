package com.java.validation.DemoValidation.api;

import com.java.validation.DemoValidation.data.CurrencyCreateRequest;
import com.java.validation.DemoValidation.data.UserRequest;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
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

  @PostMapping(path = "/currency")
  public ResponseEntity<String> createCurrency(@Valid @RequestBody CurrencyCreateRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body("Currency Created");
  }

}
