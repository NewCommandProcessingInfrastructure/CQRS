package com.java.grpc.util;

import com.java.grpc.service.StockClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

  private final StockClientService stockClientService;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Response: " + (stockClientService.getStockPrice("GOOGL")));
  }
}
