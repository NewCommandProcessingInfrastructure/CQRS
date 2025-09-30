package com.java.command.project.api;

import com.java.command.core.CommandPipeline;
import com.java.command.project.command.UserCommand;
import com.java.command.project.entity.UserRequest;
import com.java.command.project.entity.UserResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class UserController {

	private final CommandPipeline pipeline;

	@PostMapping(path = "/add-user")
	public UserResponse postMethodName(@RequestBody UserRequest request) {
		final UserCommand command = new UserCommand();

		command.setId(UUID.randomUUID());
		command.setCreatedAt(OffsetDateTime.now());
		command.setPayload(request);
		log.info("Inside UserController - postMethodName");
		final Supplier<UserResponse> response = pipeline.send(command);
		UserResponse userResponse = response.get();
		log.info("Inside UserController - userResponse");
		return userResponse;
	}
}
