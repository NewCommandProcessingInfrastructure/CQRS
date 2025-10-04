package com.java.workflow.infrastructure.persistence.middlewares;

import com.java.workflow.infrastructure.core.Command;
import com.java.workflow.infrastructure.core.CommandMiddleware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class CommandUserValidationMiddleware implements CommandMiddleware {

  @Override
  public void invoke(Command<?> command) {
    log.info("Inside CommandUserValidationMiddleware");
  }
}
