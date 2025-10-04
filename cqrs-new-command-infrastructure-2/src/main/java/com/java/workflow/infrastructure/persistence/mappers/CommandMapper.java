package com.java.workflow.infrastructure.persistence.mappers;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.persistence.domain.CommandEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy =
        InjectionStrategy.CONSTRUCTOR, uses = {CommandJsonMapper.class})
public interface CommandMapper {

  @Mapping(ignore = true, target = "id")
  @Mapping(source = "id", target = "commandId")
  @Mapping(source = "createdAt", target = "createdAt")
  @Mapping(source = "tenantId", target = "tenantId")
  @Mapping(source = "username", target = "username")
  @Mapping(source = "payload", target = "payload")
  CommandEntity map(Command source);

  Command map(CommandEntity source);
}
