package com.ronte.api.controller;

import com.ronte.api.entity.Account;
import com.ronte.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public List<Account> list() {
        return accountService.getAccounts();
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable("id") long id) {
        return accountService.getAccount(id);
    }

    @PostMapping("/")
    public void add(@RequestBody Account account) {
        accountService.addAccount(account);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") long id, @RequestBody Account account) {
        accountService.updateAccount(id, account);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        accountService.deleteAccount(id);
    }

    @GetMapping("/{id}/details")
    public String getDetails(@PathVariable("id") long id) {
        return accountService.getAccountDetails(id);
    }
}
