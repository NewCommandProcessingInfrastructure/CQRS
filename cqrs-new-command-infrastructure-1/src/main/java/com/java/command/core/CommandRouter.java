package com.java.command.core;

@FunctionalInterface
public interface CommandRouter {

	<REQ, RES> CommandHandler<REQ, RES> route(Command<REQ> command);
}
