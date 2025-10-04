package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandRouter;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.dsl.Disruptor;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
public class DisruptorCommandExecutor implements CommandExecutor, Closeable {

  private final Disruptor<CommandEvent> disruptor;

  @Override
  public <REQ, RES> Supplier<RES> execute(Command<REQ> command) {
    CommandEvent<REQ, RES> processedEvent = next(command);
    return processedEvent.getFuture()::join;
  }

  private <RES, REQ> CommandEvent<REQ,RES> next(Command<REQ> command) {
    var ringBuffer = disruptor.getRingBuffer();
    var sequenceId = ringBuffer.next();

    CommandEvent<REQ, RES> event = ringBuffer.get(sequenceId);
    event.setCommand(command);
    ringBuffer.publish(sequenceId);
    return event;
  }

  @Override
  public void close() throws IOException {
    disruptor.shutdown();
  }

  @Slf4j
  @Getter
  @Setter
  public static class CommandEvent<REQ, RES> {
    private Command<REQ> command;
    private CompletableFuture<RES> future = new CompletableFuture<>();
  }

  @Slf4j
  @RequiredArgsConstructor
  public static class CompletableCommandEventHandler implements EventHandler<CommandEvent>{
    private final List<CommandMiddleware> commandMiddlewares;
    private final CommandRouter router;

    @Override
    public void onEvent(CommandEvent event, long sequence, boolean endOfBatch) throws Exception {
      log.info("Inside CompletableCommandEventHandler - onEvent");
      try {
        for(CommandMiddleware middleware : commandMiddlewares) {
          middleware.invoke(event.getCommand());
        }

        var handler = router.route(event.getCommand());
        event.getFuture().complete(handler.handle(event.getCommand()));
      } catch(Exception e) {
        event.getFuture().completeExceptionally(e);
      }
    }
  }
}
