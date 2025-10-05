package com.java.workflow.app.data;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String username;
  private String email;
  private String firstname;
  private String lastname;
  private Integer age;
}
