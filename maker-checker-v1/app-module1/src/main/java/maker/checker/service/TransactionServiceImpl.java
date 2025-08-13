package maker.checker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maker.checker.data.CreateTransactionRequest;
import maker.checker.data.Transaction;
import maker.checker.data.TransactionStatus;
import maker.checker.repository.TransactionRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository repository;

  @Override
  @Transactional
  @PreAuthorize("hasRole('MAKER')")
  public Transaction create(CreateTransactionRequest req, Authentication auth){
    Transaction tx = Transaction.builder()
            .description(req.getDescription())
            .amount(req.getAmount())
            .status(TransactionStatus.PENDING)
            .makerUsername(auth.getName())
            .build();
    return repository.save(tx);
  }

  @Override
  @Transactional
  @PreAuthorize("hasRole('CHECKER')")
  public Transaction approve(Long id, Authentication auth){
    Transaction tx = repository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Transaction Not Found!"));

    if (!tx.getStatus().equals(TransactionStatus.PENDING)) {
      throw new IllegalArgumentException("Only PENDING transactions can be Approved!");
    }

    if (tx.getMakerUsername().equals(auth.getName())) {
      throw new IllegalArgumentException("Maker Cannot approve their Own Transaction!");
    }

    tx.setStatus(TransactionStatus.APPROVED);
    tx.setCheckerUsername(auth.getName());
    return tx;
  }

  @Override
  @Transactional
  @PreAuthorize("hasRole('CHECKER')")
  public Transaction reject(Long id, Authentication auth){
    Transaction tx = repository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Transaction Not Found!"));

    if (!tx.getStatus().equals(TransactionStatus.PENDING)) {
      throw new IllegalArgumentException("Only PENDING transactions can be Rejected!");
    }

    if (tx.getMakerUsername().equals(auth.getName())) {
      throw new IllegalArgumentException("Maker Cannot reject their Own Transaction!");
    }

    tx.setStatus(TransactionStatus.REJECTED);
    tx.setCheckerUsername(auth.getName());
    return tx;
  }
}
