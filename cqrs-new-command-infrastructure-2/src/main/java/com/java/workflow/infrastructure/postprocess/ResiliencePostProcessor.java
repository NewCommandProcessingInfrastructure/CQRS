package com.java.workflow.infrastructure.postprocess;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandPostProcessor;
import com.java.workflow.infrastructure.persistence.domain.CommandEntity;
import com.java.workflow.infrastructure.persistence.domain.CommandRepository;
import io.github.resilience4j.retry.annotation.Retry;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class ResiliencePostProcessor<T> implements CommandPostProcessor<T> {

  private final CommandRepository repository;
  private final AtomicInteger counter = new AtomicInteger(0);

  @Transactional
  @Retry(name = "run", fallbackMethod = "updateCommandStatusFallback")
  @Override
  public void run(Command<T> command) {

    /** Simulates the database error event */
//    log.info("Inside Command PostProcess for Resilience 4J");
//    CommandEntity commandEntity = repository.findByCommandId(command.getId());
//    log.info("CommandEntity, {}", commandEntity);
//    // Simulate transient DB failure on first 2 attempts
//    int attempt = counter.incrementAndGet();
//    log.info("Attempt , {}" , attempt + " to update status...");
//
//    if (attempt < 3) {
//      throw new RuntimeException("Simulated transient DB failure");
//    }
//    commandEntity.setStatus("CLOSED");
//    repository.save(commandEntity);

    log.info("Inside Command PostProcess for Resilience 4J");

    // Fetch the DB entity
    CommandEntity commandEntity = repository.findByCommandId(command.getId());
    if (commandEntity == null) {
      throw new RuntimeException("CommandEntity not found for ID: " + command.getId());
    }
    log.info("CommandEntity fetched: {}", commandEntity);

    try {
      // Update the status to CLOSED
      commandEntity.setStatus("CLOSED");
      commandEntity.setUpdatedAt(OffsetDateTime.now());
      repository.save(commandEntity);
      log.info("Command status updated to CLOSED for ID: {}", command.getId());
    } catch (DataAccessException ex) {
      // Only retry for actual DB access errors
      log.warn("Transient DB issue encountered for command ID {}: {}", command.getId(), ex.getMessage());
      throw new RuntimeException("Transient DB error, retrying...", ex);
    }
  }

  // Fallback method when all retries fail
  public void updateCommandStatusFallback(Long id, Throwable e) {
    log.error("Retries exhausted for command {}, marking as failed in memory only. Root cause: {}", id, e.getMessage());
    throw new RuntimeException("Critical failure: Unable to update command status; DB unreachable", e);
  }
}
