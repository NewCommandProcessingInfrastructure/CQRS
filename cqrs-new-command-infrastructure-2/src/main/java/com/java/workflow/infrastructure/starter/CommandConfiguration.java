package com.java.workflow.infrastructure.starter;

import com.java.workflow.infrastructure.core.CommandMiddleware;
import com.java.workflow.infrastructure.core.CommandProperties;
import com.java.workflow.infrastructure.core.CommandRouter;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(CommandProperties.class)
@ComponentScan("com.java.command.core")
@ComponentScan("com.java.command.implementation")
public class CommandConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
  WaitStrategy waitStrategy() {
    log.info("Inside CommandConfiguration - waitStrategy");
    return new YieldingWaitStrategy();
  }

  @Bean
  @ConditionalOnProperty(value = "helloworld.command.executor", havingValue = "disruptor")
  Disruptor<?> disruptor (CommandProperties properties, WaitStrategy waitStrategy, List<CommandMiddleware> middlewares, CommandRouter router) {
    Disruptor<DisruptorCommandExecutor.CommandEvent> disruptor =
            new Disruptor<>(DisruptorCommandExecutor.CommandEvent::new,
                    properties.getRingBufferSize(),
                    DaemonThreadFactory.INSTANCE,
                    properties.getProducerType(),
                    waitStrategy);

    log.info("Inside CommandConfiguration - disruptor");

    disruptor.handleEventsWith(new DisruptorCommandExecutor.CompletableCommandEventHandler(middlewares, router));
    disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());
    disruptor.start();

    return disruptor;
  }
}
