package com.java.command.implementation;

import com.java.command.core.Command;
import com.java.command.core.CommandHandler;
import com.java.command.core.CommandRouter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnBean(CommandRouter.class)
public class DefaultCommandRouter implements CommandRouter {

	private final List<CommandHandler<?,?>> commandHandlers;

	@Override
	public <REQ, RES> CommandHandler<REQ, RES> route(Command<REQ> command) {
		if(command == null) {
			throw new RuntimeException("Command Handler Not Found");
		}
//		log.info("Inside DefaultCommandRouter - route");
		return (CommandHandler<REQ, RES>) commandHandlers
        .stream()
        .filter(handler -> handler.matches(command))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(command.getId().toString()));
	}
}
