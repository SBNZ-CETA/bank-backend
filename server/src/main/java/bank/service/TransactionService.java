package bank.service;

import bank.repository.TransactionRepository;
import facts.BankAccount;
import facts.Transaction;
import facts.UserTransactions;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        transaction.setTransactionTime(LocalDateTime.now());

        if(isTransactionsSuspicious(transaction)){
            transaction.setSuspicious(true);
            return transactionRepository.save(transaction);
        }
        transaction.setSuspicious(false);
        transferMoney(transaction.getAmount(), transaction.getSenderAccount(), transaction.getReceiverAccount());

        return transactionRepository.save(transaction);
    }

    public boolean isTransactionsSuspicious(Transaction transaction){
        KieSession kieSession = kieContainer.newKieSession();
        UserTransactions userTransactions = new UserTransactions(transaction.getSenderAccount().getUser().getId());
        userTransactions.setTransactionList(getAllSendersTransactions(userTransactions.getUserId()));

        kieSession.insert(userTransactions);
        kieSession.insert(transaction);
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("locationIndependent").setFocus();
        kieSession.fireAllRules();

        kieSession.dispose();
        return transaction.isSuspicious();
    }

    public List<Transaction> getAllSendersTransactions(Long userId){
        List<Transaction> transactions = getAll();
        List<Transaction> transactionsSender = new ArrayList<Transaction>();
        for (Transaction t: transactions) {
            if (Objects.equals(t.getSenderAccount().getUser().getId(), userId))
                if(!t.isSuspicious())
                    transactionsSender.add(t);
        }
        return transactionsSender;
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

