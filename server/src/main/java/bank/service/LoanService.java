package bank.service;

import bank.repository.LoanRepository;
import dtos.LoanRequestDto;
import facts.Loan;
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
   private KieContainer kieContainer;

   public List<Loan> processLoanRequest(LoanRequestDto request, User user)  {
      KieSession kieSession = kieContainer.newKieSession();

      loanRepository.findAll().forEach(kieSession::insert);
      kieSession.insert(user);
      kieSession.insert(request);
      //kieSession.fireAllRules();

      QueryResults result = kieSession.getQueryResults("findFittingLoan", new Object[]{Variable.v});

      List<Loan> loansAvailable = new ArrayList<>();
      result.forEach(loan -> loansAvailable.add(loanRepository.findById((Long)loan.get("$id")).get()));

      kieSession.dispose();
      return loansAvailable;
   }
}
