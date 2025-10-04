package com.java.workflow.infrastructure.persistence.middlewares;

import com.fasterxml.jackson.databind.JsonNode;
import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.persistence.domain.CommandEntity;
import com.java.workflow.infrastructure.persistence.domain.CommandRepository;
import com.java.workflow.infrastructure.persistence.mappers.CommandJsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(value = 2)
@Component
public class CommandPersistanceMiddleware implements CommandMiddleware {

  private final CommandRepository commandRepository;
  private final CommandJsonMapper commandJsonMapper;

  @Override
  public void invoke(Command<?> command) {
    if (command == null) {
      return;
    }

    try {
      JsonNode payloadJson = commandJsonMapper.map(command.getPayload());
      CommandEntity entity = new CommandEntity();

      entity.setCommandId(command.getId());
      entity.setPayload(payloadJson);
      entity.setCreatedAt(command.getCreatedAt());

      commandRepository.save(entity);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
