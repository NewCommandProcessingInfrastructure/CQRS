package com.java.workflow.infrastructure.persistence.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<CommandEntity, Long>,
        JpaSpecificationExecutor<CommandEntity> {
  CommandEntity findByCommandId(UUID id);
}
