package bank.controller;

import bank.service.BankAccountService;
import bank.service.TransactionService;
import dtos.TransactionDto;
import facts.Transaction;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/transactions")
@AllArgsConstructor
public class TransactionController {
    private TransactionService transactionService;
    private BankAccountService bankAccountService;

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @GetMapping("/not-sus/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id) {
        Transaction transaction = transactionService.getById(id);
        return ResponseEntity.ok(transactionService.notSuspicious(transaction));
    }

    @PostMapping("/process")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDto) {
        if(transactionDto.isPayingFromBookstore()){
            if (!bankAccountService.validateCreditCardData(transactionDto)) {
                return ResponseEntity.badRequest().build();
            }
        }
        Transaction transaction = transactionService.processTransaction(getObjectFromDto(transactionDto));
        return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.badRequest().build();
    }

    public Transaction getObjectFromDto(TransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setLocation(dto.getLocation());
        transaction.setSenderAccount(bankAccountService.getById(dto.getSenderAccountId()));
        transaction.setReceiverAccount(bankAccountService.getById(dto.getReceiverAccountId()));
        return transaction;
    }
}
