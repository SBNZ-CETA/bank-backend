package bank.controller;

import bank.service.BankAccountService;
import bank.service.UserService;
import dtos.BankAccountDto;
import dtos.NewBankAccountDto;
import facts.BankAccount;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/accounts")
@AllArgsConstructor
public class BankAccountController {
    private BankAccountService accountService;
    private UserService userService;

    @PostMapping(produces = "application/json")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<BankAccountDto> create(@RequestBody NewBankAccountDto newAccount) {
        ModelMapper mapper = new ModelMapper();
        return new ResponseEntity<>(mapper.map(
                accountService.create(newAccount, userService.getLoggedId()),
                BankAccountDto.class
        ), HttpStatus.CREATED);
    }
}
