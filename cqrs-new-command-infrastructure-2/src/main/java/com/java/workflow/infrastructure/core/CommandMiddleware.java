package com.java.workflow.infrastructure.core;

public interface CommandMiddleware {
  void invoke(Command<?> command);
  default void postProcess(Command<?> command, Object result, Throwable exception) {}
}
