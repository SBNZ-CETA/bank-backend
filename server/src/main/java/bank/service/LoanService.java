package bank.service;

import bank.repository.BankAccountRepository;
import bank.repository.LoanRepository;
import bank.repository.LoanRequestRepository;
import dtos.LoanRequestDto;
import facts.Loan;
import facts.LoanRequest;
import facts.User;
import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.Variable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {
   private LoanRepository loanRepository;
   private BankAccountRepository bankAccountRepository;
   private LoanRequestRepository loanRequestRepository;
   private KieContainer kieContainer;

   public List<Loan> processLoanRequest(Long requestId)  {
      KieSession kieSession = kieContainer.newKieSession();

      loanRepository.findAll().forEach(kieSession::insert);
      LoanRequest request = loanRequestRepository.getOne(requestId);
      User user = request.getUser();
      kieSession.insert(request);
      kieSession.insert(user);
      kieSession.insert(bankAccountRepository.getByUserId(user.getId()).get());

      QueryResults result = kieSession.getQueryResults("findFittingLoan", new Object[]{Variable.v});

      List<Loan> loansAvailable = new ArrayList<>();
      result.forEach(loan -> loansAvailable.add(loanRepository.findById((Long)loan.get("$id")).get()));

      System.out.println(loansAvailable.size());

      kieSession.dispose();
      return loansAvailable;
   }

   public LoanRequest createLoanRequest(LoanRequestDto request, User user) {
      return this.loanRequestRepository.save(new LoanRequest(request, user));
   }

   public List<LoanRequest> getAll() {
      return this.loanRequestRepository.findAll();
   }
}
