package com.java.command.implementation;

import com.java.command.core.Command;
import com.java.command.core.CommandExecutor;
import com.java.command.core.CommandMiddleware;
import com.java.command.core.CommandPipeline;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnBean(CommandPipeline.class)
public class DefaultCommandPipeline implements CommandPipeline {

	private final CommandExecutor executor;
	private final List<CommandMiddleware> middlewares;

	@Override
	public <REQ, RES> Supplier<RES> send(Command<REQ> command) {
		Objects.requireNonNull(command, "Command must not be null.");
		log.info("Inside DefaultCommandPipeline - send - before middlewares");
		middlewares.forEach(mw -> mw.invoke(command));
		log.info("Inside DefaultCommandPipeline - send - after middlewares");
		return executor.execute(command);
	}
}
