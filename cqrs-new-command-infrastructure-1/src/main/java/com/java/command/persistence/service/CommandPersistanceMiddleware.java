package com.java.command.persistence.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.java.command.core.Command;
import com.java.command.core.CommandMiddleware;
import com.java.command.persistence.domain.CommandEntity;
import com.java.command.persistence.domain.CommandRepository;
import com.java.command.persistence.mapping.CommandJsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandPersistanceMiddleware implements CommandMiddleware {

	private final CommandRepository commandRepository;
	private final CommandJsonMapper commandJsonMapper;

	@Override
	public void invoke(Command<?> command) {

		if(command == null) {
			log.info("Received Null command, skipping persistance");
		}

//		log.info("Inside CommandPersistanceMiddleware - invoke");

		try {
			JsonNode payloadJson = commandJsonMapper.map(command.getPayload());
			CommandEntity entity = new CommandEntity();

			entity.setCommandId(command.getId());
			entity.setPayload(payloadJson);
			entity.setCreatedAt(command.getCreatedAt());

			commandRepository.save(entity);

//			log.info("Inside CommandPersistanceMiddleware - after database Save");
//			log.info("Persisted command: {} with payload type: {} to m_command.", command.getId(), command.getPayload() != null  ? command.getPayload().getClass().getSimpleName() : "null");
		} catch (Exception e) {
			log.error("Failed to persist command, {}", command.getId(), e);
		}
	}
}
