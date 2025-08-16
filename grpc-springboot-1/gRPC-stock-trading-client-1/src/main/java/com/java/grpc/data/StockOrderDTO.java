package com.java.grpc.data;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockOrderDTO implements Serializable {
  private String orderId;
  private String stockSymbol;
  private int quantity;
  private double price;
  private String orderType;
}
