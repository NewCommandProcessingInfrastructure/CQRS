package com.java.grpc.service;

import com.java.grpc.entity.Stock;
import com.java.grpc.repository.StockRepository;
import com.java.stock.generated.StockRequest;
import com.java.stock.generated.StockResponse;
import com.java.stock.generated.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@GrpcService
@RequiredArgsConstructor
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

  private final StockRepository repository;

  @Override
  public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

    String stockSymbol = request.getStockSymbol();
    Stock stockEntity = repository.findByStockSymbol(stockSymbol);
//    Stock stockEntity = repository.fakeStock();
//    Stock stockEntity = Stock.builder()
//            .stockSymbol("GOOGL")
//            .price(123.123)
//            .lastUpdated(LocalDateTime.now())
//            .build();

    StockResponse stockResponse = StockResponse.newBuilder()
            .setStockSymbol(stockEntity.getStockSymbol())
            .setPrice(stockEntity.getPrice())
            .setTimestamp(stockEntity.getLastUpdated().toString())
            .build();

    responseObserver.onNext(stockResponse);
    responseObserver.onCompleted();
  }

  @Override
  public void subscribeStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {
    String symbol = request.getStockSymbol();
    try {
      for(int i=0; i<10; i++) {
        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockSymbol(symbol)
                .setPrice(new Random().nextDouble(200))
                .setTimestamp(Instant.now().toString())
                .build();
        responseObserver.onNext(stockResponse);
        TimeUnit.SECONDS.sleep(1);
      }
      responseObserver.onCompleted();
    } catch (Exception e){

    }
  }
}
