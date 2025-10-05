package com.java.workflow.app.handlers;

import com.java.workflow.app.data.UserRequest;
import com.java.workflow.app.data.UserResponse;
import com.java.workflow.app.service.UserService;
import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommandHandler implements CommandHandler<UserRequest, UserResponse> {

  private final UserService service;

  @Override
  public UserResponse handle(Command<UserRequest> command) {
    return service.addUserToDatabase(command.getPayload());
  }
}
