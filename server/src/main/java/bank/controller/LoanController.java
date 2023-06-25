package bank.controller;

import bank.service.LoanService;
import bank.service.UserService;
import dtos.LoanRequestDto;
import facts.Loan;
import facts.LoanRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/loans")
@AllArgsConstructor
public class LoanController {
    private LoanService loanService;
    private UserService userService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<LoanRequest> requestLoan(@RequestBody LoanRequestDto request){
        return new ResponseEntity<>(loanService.createLoanRequest(request, userService.getLoggedId()), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json", path = "/requests/{id}")
    public ResponseEntity<List<Loan>> getReccommendation(@PathVariable Long id){
        return new ResponseEntity<>(loanService.processLoanRequest(id), HttpStatus.OK);
    }
    @GetMapping(produces = "application/json", path = "/requests")
    public ResponseEntity<List<LoanRequest>> getAllRequests(){
        return new ResponseEntity<>(loanService.getAll(), HttpStatus.OK);
    }
}