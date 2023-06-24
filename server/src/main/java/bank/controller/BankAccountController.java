package bank.controller;

import bank.service.BankAccountService;
import bank.service.UserService;
import dtos.BankAccountDto;
import dtos.NewBankAccountDto;
import facts.BankAccount;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/accounts")
@AllArgsConstructor
public class BankAccountController {
    private BankAccountService accountService;
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<BankAccount>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }
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
