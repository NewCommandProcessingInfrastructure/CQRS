package com.java.validation.DemoValidation.data;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest implements Serializable {

  @NotBlank(message = "{user.name.notblank}")
  private String name;

  @Email(message = "{user.email.invalid}")
  private String email;

  @Min(value = 18, message = "{user.age.min}")
  private int age;
}
