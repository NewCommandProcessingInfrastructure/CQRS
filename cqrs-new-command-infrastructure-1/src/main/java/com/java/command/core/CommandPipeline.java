package com.java.command.core;

import java.util.function.Supplier;

public interface CommandPipeline {

	<REQ, RES> Supplier<RES> send(Command<REQ> command);
}
