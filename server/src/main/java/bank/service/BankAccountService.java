package bank.service;

import bank.repository.BankAccountRepository;
import dtos.NewBankAccountDto;
import facts.BankAccount;
import facts.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class BankAccountService {
   private BankAccountRepository accountRepository;

   public BankAccount create(NewBankAccountDto accountDto, User user) {
      return accountRepository.save(generateNewAccount(user, accountDto.getBalance()));
   }

   private BankAccount generateNewAccount(User user, Double balance) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, 2);
      Date expirationDate = cal.getTime();

      Integer ccv = (new Random()).nextInt(1000);

      return new BankAccount(user, ccv, expirationDate, balance);
   }
}
