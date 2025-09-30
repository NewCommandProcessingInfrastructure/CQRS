package com.java.command.project.handlers;

import com.java.command.core.Command;
import com.java.command.core.CommandHandler;
import com.java.command.project.entity.UserRequest;
import com.java.command.project.entity.UserResponse;
import com.java.command.project.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommandHandler implements CommandHandler<UserRequest, UserResponse> {

	private final UserEntityService service;

	@Override
	public UserResponse handle(Command<UserRequest> command) {
		log.info("Inside UserCommandHandler - handle");
		return service.addUserToDatabase(command.getPayload());
	}
}
