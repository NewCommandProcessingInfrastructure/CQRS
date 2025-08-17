package com.java.grpc.service;

import com.java.grpc.entity.Stock;
import com.java.grpc.repository.StockRepository;
import com.java.stock.generated.OrderSummary;
import com.java.stock.generated.StockOrder;
import com.java.stock.generated.StockRequest;
import com.java.stock.generated.StockResponse;
import com.java.stock.generated.StockTradingServiceGrpc;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.java.stock.generated.TradeStatus;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

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

  @Override
  public StreamObserver<StockOrder> bulkStockOrder(StreamObserver<OrderSummary> responseObserver) {
    return new StreamObserver<StockOrder>() {
      private int totalOrders = 0;
      private double totalAmount = 0.0;
      private int successCount = 0;

      @Override
      public void onNext(StockOrder stockOrder) {
        totalOrders++;
        totalAmount += stockOrder.getPrice() * stockOrder.getQuantity();
        successCount++;
        System.out.println("Received Order: " + stockOrder);
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed!");
        OrderSummary summary = OrderSummary.newBuilder()
                .setTotalOrders(totalOrders)
                .setTotalAmount(totalAmount)
                .setSuccessCount(successCount)
                .build();
        responseObserver.onNext(summary);
        responseObserver.onCompleted();
      }
    };
  }

  @Override
  public StreamObserver<StockOrder> liveTrading(StreamObserver<TradeStatus> responseObserver) {
    return new StreamObserver<>() {
      @Override
      public void onNext(StockOrder stockOrder) {
        System.out.println("Received Order: " + stockOrder);
        String status = "EXECUTED";
        String message = "Order Placed Successfully!";
        if(stockOrder.getQuantity() <= 0){
          status="FAILED";
          message="Invalid Quantity";
        }
        TradeStatus tradeStatus = TradeStatus.newBuilder()
                .setOrderId(stockOrder.getOrderId())
                .setMessage(message)
                .setStatus(status)
                .setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE))
                .build();

        responseObserver.onNext(tradeStatus);
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed!");
      }
    };
  }
}
