package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandPipeline;
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

  @Override
  public <REQ, RES> Supplier<RES> send(Command<RES> command) {
    Objects.requireNonNull(command, "Command Must Not Be Null.");
    return executor.execute(command);
  }
}
