package bank.service;

import bank.repository.TransactionRepository;
import facts.BankAccount;
import facts.Transaction;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private TransactionRepository transactionRepository;
    private BankAccountService bankAccountService;
    private KieContainer kieContainer;

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id).get();
    }

    public Transaction processTransaction(Transaction transaction){
        if (!isTransactionValid(transaction))
            return null;
        if(isTransactionsSuspicious(transaction)){
            transaction.setSuspicious(true);
            return transactionRepository.save(transaction);
        }
        transaction.setSuspicious(false);
        transferMoney(transaction.getAmount(), transaction.getSenderAccount(), transaction.getReceiverAccount());
        transaction.setTransactionTime(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public boolean isTransactionsSuspicious(Transaction transaction){
        return false;
    }

    public boolean isTransactionValid(Transaction transaction){
        //front will return valid account if it can be built from entered id, cvv and expireDate of the card
        if (transaction.getSenderAccount() == null)
            return false;
        if (transaction.getReceiverAccount() == null)
            return false;
        if(transaction.getSenderAccount().getBalance() < transaction.getAmount())
            return false;
        return true;
    }

    public void transferMoney(int amount, BankAccount sender, BankAccount receiver){
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        bankAccountService.updateAccount(sender);
        bankAccountService.updateAccount(receiver);
    }
}

