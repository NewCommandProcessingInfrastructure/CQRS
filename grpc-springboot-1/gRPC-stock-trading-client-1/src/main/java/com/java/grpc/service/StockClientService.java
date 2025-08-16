package com.java.grpc.service;

import com.java.stock.generated.StockRequest;
import com.java.stock.generated.StockResponse;
import com.java.stock.generated.StockTradingServiceGrpc;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

  @GrpcClient("stockService")
  private StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;

  public StockResponse getStockPrice(String stockSymbol) {
    StockRequest request = StockRequest.newBuilder()
            .setStockSymbol(stockSymbol)
            .build();

    return serviceBlockingStub.getStockPrice(request);
  }
}
