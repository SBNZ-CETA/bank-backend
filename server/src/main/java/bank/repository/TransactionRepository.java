package bank.repository;

import facts.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository   extends JpaRepository<Transaction, Long> {
}
