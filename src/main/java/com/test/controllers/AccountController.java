package com.test.controllers;

import com.test.common.Paths;
import com.test.dtos.account.AccountDto;
import com.test.dtos.account.CreateAccountDto;
import com.test.entities.Account;
import com.test.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Paths.ACCOUNTS)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id:[\\d]+}")
    public AccountDto getAccount(@PathVariable Long id) {
        return accountService.get(id).toDto();
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @PostMapping
    public AccountDto createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return accountService.create(createAccountDto.toEntity()).toDto();
    }

    @PutMapping(path = "/{id:[\\d]+}")
    public AccountDto updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        Account account = accountDto.toEntity();
        account.setId(id);
        return accountService.update(account).toDto();
    }

    @PreAuthorize("hasRole(\"ADMIN\")")
    @DeleteMapping(path = "/{id:[\\d]+}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
    }
}
