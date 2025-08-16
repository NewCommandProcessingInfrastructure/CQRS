package com.java.grpc.service;

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
}
