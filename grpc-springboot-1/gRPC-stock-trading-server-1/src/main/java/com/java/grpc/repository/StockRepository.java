package com.java.grpc.repository;

import com.java.grpc.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface StockRepository extends JpaRepository<Stock, Long> {
  Stock findByStockSymbol(String stockSymbol);

  default Stock fakeStock() {
    return Stock.builder()
            .stockSymbol("GOOGL")
            .price(123.123)
            .lastUpdated(LocalDateTime.now())
            .build();
  }
}
