package com.java.workflow.infrastructure.core;

import java.util.function.Supplier;

public interface CommandPipeline {
  <REQ, RES>Supplier<RES> send(Command<RES> command);
}
