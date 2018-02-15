package com.accounts.web;

import com.accounts.api.model.Account;
import com.accounts.service.AccountNotFoundException;
import com.accounts.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Created by pasha on 14.02.18.
 */
@RestController
@RequestMapping(value = "accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("")
    public List<Account> retrieveAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account retrieveAccount(@PathVariable long id) {
        Optional<Account> account = accountService.findById(id);

        if (!account.isPresent())
            throw new AccountNotFoundException("id-" + id);

        return account.get();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable long id) {
        accountService.deleteById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createAccount(@RequestBody Account account) {
        Account savedAccount = accountService.save(account);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedAccount.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAccount(@RequestBody Account account, @PathVariable long id) {

        Optional<Account> accountOptional = accountService.findById(id);

        if (!accountOptional.isPresent())
            return ResponseEntity.notFound().build();

        account.setId(id);

        accountService.save(account);

        return ResponseEntity.noContent().build();
    }
}
