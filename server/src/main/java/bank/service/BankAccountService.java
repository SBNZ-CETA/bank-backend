package bank.service;

import bank.repository.BankAccountRepository;
import dtos.NewBankAccountDto;
import dtos.TransactionDto;
import facts.BankAccount;
import facts.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class BankAccountService {
   private BankAccountRepository accountRepository;

   public List<BankAccount> getAll() {
      return accountRepository.findAll();
   }

   public BankAccount getById(Long id) {
      return accountRepository.findById(id).get();
   }

   public BankAccount create(NewBankAccountDto accountDto, User user) {
      return accountRepository.save(generateNewAccount(user, accountDto));
   }

   public boolean validateCreditCardData(TransactionDto transactionDto) {
      BankAccount senderAccount = getById(transactionDto.getSenderAccountId());
      BankAccount receiverAccount = getById(transactionDto.getReceiverAccountId());
      if (senderAccount == null && receiverAccount==null) {
         return false;
      }
      Date expirationDate = new Date(senderAccount.getExpirationDate().getTime());
      String fullName = senderAccount.getUser().getName() + " " + senderAccount.getUser().getSurname();
      Date today = new Date();
      if (!senderAccount.getCcv().equals(transactionDto.getCcv()) ||
              !fullName.equals(transactionDto.getOwnerName()) || !transactionDto.getExpirationDate().equals(expirationDate) || today.after(expirationDate)) {
         return false;
      }

      return true;
   }

   private BankAccount generateNewAccount(User user, NewBankAccountDto newAccount) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, 2);
      Date expirationDate = cal.getTime();

      Integer ccv = (new Random()).nextInt(1000);

      return new BankAccount(
              user,
              ccv,
              expirationDate,
              newAccount.getBalance(),
              newAccount.getIncome()
      );
   }

   public BankAccount updateAccount(BankAccount bankAccount) {
      return accountRepository.save(bankAccount);
   }
}
