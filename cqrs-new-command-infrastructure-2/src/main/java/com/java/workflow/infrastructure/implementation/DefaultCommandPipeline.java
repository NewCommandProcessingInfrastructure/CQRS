package com.java.workflow.infrastructure.implementation;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandExecutor;
import com.java.workflow.infrastructure.core.CommandPipeline;
import com.java.workflow.infrastructure.core.CommandPostProcessor;
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
  private final List<CommandPostProcessor<?>> postProcesses;

  @Override
  public <REQ, RES> Supplier<RES> send(Command<REQ> command) {
    Objects.requireNonNull(command, "Command Must Not Be Null.");
    // Dependency Inject - PostProcessing
    // Base supplier executes handler + middleware
    Supplier<RES> baseSupplier = executor.execute(command);

    // Wrap the base supplier with post-processing
    return () -> {
      RES result = null;
      Throwable exception = null;

      try {
        // Execute the handler logic
        result = baseSupplier.get();
      } catch (Throwable ex) {
        exception = ex;
      }

      // Execute post-processing with both result and exception
      for(CommandPostProcessor<?> postProcess : postProcesses) {
        CommandPostProcessor<REQ> typedProcessor = (CommandPostProcessor<REQ>) postProcess;
        typedProcessor.run(command);
      }

      // If exception occurred, propagate it
      if (exception != null) {
        throw new RuntimeException("Handler execution failed", exception);
      }
      return result;
    };
  }
}
