package maker.checker.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maker.checker.data.CreateTransactionRequest;
import maker.checker.data.Transaction;
import maker.checker.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService service;

  @PostMapping(path = "/transactions", version = "1")
  public ResponseEntity<Transaction> createv1(@Valid @RequestBody CreateTransactionRequest req,
                                            Authentication auth){
    return ResponseEntity.status(HttpStatus.OK).body(service.create(req, auth));
  }

  @PostMapping(path = "/transactions", version = "2")
  public ResponseEntity<Transaction> createv2(@Valid @RequestBody CreateTransactionRequest req,
                                            Authentication auth){
    return ResponseEntity.status(HttpStatus.OK).body(new Transaction());
  }

  @PostMapping(path = "/transactions/{id}/approve", version = "1")
  public ResponseEntity<Transaction> approve(@PathVariable Long id,
                                            Authentication auth){
    return ResponseEntity.status(HttpStatus.OK).body(service.approve(id, auth));
  }

  @PostMapping(path = "/transactions/{id}/reject", version = "1")
  public ResponseEntity<Transaction> reject(@PathVariable Long id,
                                            Authentication auth){
    return ResponseEntity.status(HttpStatus.OK).body(service.reject(id, auth));
  }
}
