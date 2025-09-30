package com.java.command.core;

import com.google.common.reflect.TypeToken;

public interface CommandHandler<REQ, RES> {

	RES handle(Command<REQ> command);

	default boolean matches(Command<?> command) {
		TypeToken<REQ> handlerType = new TypeToken<>(getClass()) {};
		return handlerType
				.getRawType()
				.isAssignableFrom(command.getPayload().getClass());
	}
}
