package com.java.command.implementation;

import com.java.command.core.Command;
import com.java.command.core.CommandExecutor;
import com.java.command.core.CommandHandler;
import com.java.command.core.CommandMiddleware;
import com.java.command.core.CommandRouter;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "sync")
public class SynchronousCommandExecutor implements CommandExecutor {

	private final List<CommandMiddleware> middlewares;
	private final CommandRouter router;

	@Override
	public <REQ, RES> Supplier<RES> execute(Command<REQ> command) {
		log.info("Inside SynchronousCommandExecutor - Execute");
		for(CommandMiddleware middleware: middlewares) {
			log.info("Inside Middleware Loop");
			middleware.invoke(command);
		}

		CommandHandler<REQ, RES> handler = router.route(command);
		return () -> handler.handle(command);
	}
}
