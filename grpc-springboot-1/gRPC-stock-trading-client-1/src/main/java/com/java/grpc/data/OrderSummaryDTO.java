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
public class OrderSummaryDTO implements Serializable {
  private int totalOrders;
  private int successCount;
  private double totalAmount;
}
