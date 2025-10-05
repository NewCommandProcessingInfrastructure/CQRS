package com.java.workflow.app.command;

import com.java.workflow.app.data.UserRequest;
import com.java.workflow.infrastructure.core.Command;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommand extends Command<UserRequest> {

  @Serial
  private static final long serialVersionUID = 1L;
}
