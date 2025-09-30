package com.java.command.core;

import java.util.function.Supplier;

public interface CommandExecutor {

	<REQ, RES> Supplier<RES> execute(Command<REQ> command);
}
