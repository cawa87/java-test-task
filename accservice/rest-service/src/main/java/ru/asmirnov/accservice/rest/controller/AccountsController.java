package ru.asmirnov.accservice.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.asmirnov.accservice.exception.ScnetServiceClientException;
import ru.asmirnov.accservice.rest.dto.AccountDto;
import ru.asmirnov.accservice.service.AccountService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexey Smirnov at 27/03/2018
 */
@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountDto accountDto) throws ScnetServiceClientException {
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @PutMapping
    public ResponseEntity<AccountDto> updateAccount(@Valid @RequestBody AccountDto accountDto) throws ScnetServiceClientException {
        if (accountService.getById(accountDto.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccountList() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getById(id);
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<AccountDto> deleteById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getById(id);
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accountService.deleteById(Long.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}