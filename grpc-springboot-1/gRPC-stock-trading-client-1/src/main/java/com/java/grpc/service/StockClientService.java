package com.java.grpc.service;

import com.java.stock.generated.OrderSummary;
import com.java.stock.generated.StockOrder;
import com.java.stock.generated.StockRequest;
import com.java.stock.generated.StockResponse;
import com.java.stock.generated.StockTradingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientService {

//  @GrpcClient("stockService")
//  private StockTradingServiceGrpc.StockTradingServiceBlockingStub serviceBlockingStub;
//
//  public StockResponse getStockPrice(String stockSymbol) {
//    StockRequest request = StockRequest.newBuilder()
//            .setStockSymbol(stockSymbol)
//            .build();
//
//    return serviceBlockingStub.getStockPrice(request);
//  }

  @GrpcClient("stockService")
  private StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

  public void subscribeStockPrice(String symbol) {
    StockRequest request = StockRequest.newBuilder()
            .setStockSymbol(symbol)
            .build();

    stockTradingServiceStub.subscribeStockPrice(request, new StreamObserver<StockResponse>() {
      @Override
      public void onNext(StockResponse stockResponse) {
        System.out.println(stockResponse.getStockSymbol() + ":"
                + stockResponse.getPrice() + ":"
                + stockResponse.getTimestamp());
      }

      @Override
      public void onError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
      }

      @Override
      public void onCompleted() {
        System.out.println("Completed!");
      }
    });
  }

  public void placeBulkOrder() {
    StreamObserver<OrderSummary> responseObserver = new StreamObserver<OrderSummary>() {
      @Override
      public void onNext(OrderSummary summary) {
        System.out.println("Order Summary Received From Server:");
        System.out.println("Total Orders: " + summary.getTotalOrders());
        System.out.println("Successful Orders: " + summary.getSuccessCount());
        System.out.println("Total Amount: $" + summary.getTotalAmount());
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

    StreamObserver<StockOrder> requestObserver =
            stockTradingServiceStub.bulkStockOrder(responseObserver);

    // send multiple stream of stock order message/request
    try {
        requestObserver.onNext(StockOrder.newBuilder()
                .setOrderId("1")
                .setStockSymbol("AAPL")
                .setOrderType("BUY")
                .setPrice(150.5)
                .setQuantity(10)
                .build());

        requestObserver.onNext(StockOrder.newBuilder()
                .setOrderId("2")
                .setStockSymbol("GOOGL")
                .setOrderType("SELL")
                .setPrice(2700.0)
                .setQuantity(5)
                .build());

        requestObserver.onNext(StockOrder.newBuilder()
                .setOrderId("3")
                .setStockSymbol("TSLA")
                .setOrderType("BUY")
                .setPrice(700.0)
                .setQuantity(8)
                .build());

        // Done sending orders
        requestObserver.onCompleted();
    } catch (Exception e) {
      requestObserver.onError(e);
    }
  }
}
