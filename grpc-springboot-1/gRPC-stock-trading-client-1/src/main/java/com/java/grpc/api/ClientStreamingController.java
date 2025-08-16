package com.java.grpc.api;

import com.java.grpc.data.OrderSummaryDTO;
import com.java.grpc.data.StockOrderDTO;
import com.java.grpc.service.StockClientServiceWithUI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class ClientStreamingController {

  private final StockClientServiceWithUI stockClientService;

  @PostMapping("/bulk-order")
  public OrderSummaryDTO placeBulkOrder(@RequestBody List<StockOrderDTO> orders) {
    return stockClientService.sendBulkOrders(orders);
  }
}
