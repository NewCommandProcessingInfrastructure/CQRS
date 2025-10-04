package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandHandler;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandRouter;
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
public class SynchronousCommandExecutor  implements CommandExecutor {

  private final List<CommandMiddleware> middlewares;
  private final CommandRouter router;

  @Override
  public <REQ, RES> Supplier<RES> execute(Command<REQ> command) {
    for(CommandMiddleware middleware : middlewares) {
      middleware.invoke(command);
    }
    CommandHandler<REQ, RES> handler = router.route(command);
    return (() -> handler.handle(command));
  }
}
