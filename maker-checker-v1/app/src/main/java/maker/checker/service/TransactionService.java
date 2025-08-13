package maker.checker.service;

import maker.checker.data.CreateTransactionRequest;
import maker.checker.data.Transaction;
import org.springframework.security.core.Authentication;

public interface TransactionService {
  public Transaction create(CreateTransactionRequest req, Authentication auth);

  public Transaction approve(Long id, Authentication auth);

  public Transaction reject(Long id, Authentication auth);
}
