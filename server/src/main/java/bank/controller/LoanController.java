package bank.controller;

import bank.service.LoanService;
import bank.service.UserService;
import dtos.LoanRequestDto;
import facts.Loan;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/loans")
@AllArgsConstructor
public class LoanController {
    private LoanService loanService;
    private UserService userService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<Loan> requestLoan(@RequestBody LoanRequestDto request){
        return new ResponseEntity<>(loanService.processLoanRequest(request, userService.getLoggedId()), HttpStatus.OK);
    }
}