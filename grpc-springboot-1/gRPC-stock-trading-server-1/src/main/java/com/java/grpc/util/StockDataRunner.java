package com.java.grpc.util;

import com.java.grpc.entity.Stock;
import com.java.grpc.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockDataRunner implements CommandLineRunner {

  private final StockRepository stockRepository;

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Fetching all stocks from DB...");

    List<Stock> stocks = stockRepository.findAll();

    if (stocks.isEmpty()) {
      System.out.println("⚠️ No stocks found in database.");
    } else {
      stocks.forEach(stock ->
              System.out.println("✅ " + stock.getStockSymbol() + " - " + stock.getPrice())
      );
    }
  }
}
