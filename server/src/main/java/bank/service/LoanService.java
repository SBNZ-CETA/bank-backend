package bank.service;

import bank.repository.LoanRepository;
import dtos.LoanRequestDto;
import facts.Loan;
import facts.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanService {
   public LoanRepository loanRepository;
   public Loan processLoanRequest(LoanRequestDto request, User user)  {
       //TODO: Import user and his transactions
       //TODO: Make kie session and fire all rules
       //TODO: Pull query result from session
      return null;
   }
}
