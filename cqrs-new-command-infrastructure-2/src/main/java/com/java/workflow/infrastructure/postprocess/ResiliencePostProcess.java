package com.java.workflow.infrastructure.postprocess;

import com.java.workflow.infrastructure.core.CommandPostProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Order(value = 1)
@Component
public class ResiliencePostProcess implements CommandPostProcess {

  @Override
  public void run() {
    log.info("Inside Command PostProcess for Resilience 4J");
  }
}
