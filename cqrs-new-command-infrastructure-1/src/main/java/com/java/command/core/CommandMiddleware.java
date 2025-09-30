package com.java.command.core;

@FunctionalInterface
public interface CommandMiddleware {
	
	void invoke(Command<?> command);
}
