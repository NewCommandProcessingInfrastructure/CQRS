package com.java.command.persistence.service;

import com.java.command.core.Command;
import com.java.command.core.CommandMiddleware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class CommandUserValidationMiddleware implements CommandMiddleware {

	@Override
	public void invoke(Command<?> command) {
		log.info("Inside CommandUserValidationMiddleware");
	}
}
