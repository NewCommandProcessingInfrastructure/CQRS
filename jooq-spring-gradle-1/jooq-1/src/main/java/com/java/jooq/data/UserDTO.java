package com.java.jooq.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

  private String username;

  @Email
  private String email;
  private String firstname;
  private String lastname;

  @PositiveOrZero
  private Integer age;
}
