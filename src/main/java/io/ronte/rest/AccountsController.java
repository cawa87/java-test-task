package io.ronte.rest;

import io.ronte.model.Account;
import io.ronte.service.AccountNotFoundException;
import io.ronte.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Future<Account> create(@RequestBody Account account) {
        return CompletableFuture.supplyAsync(() -> accountService.save(account));
    }

    @GetMapping(value = "/{id}")
    public Future<Account> getOne(@PathVariable long id) {
        return CompletableFuture
                .supplyAsync(() -> accountService.findById(id))
                .exceptionally(e -> {
                    throw (RuntimeException) e.getCause();
                });
    }

    @PutMapping(value = "/{id}")
    public Future<Account> updateOne(@RequestBody Account account, @PathVariable long id) {
        return CompletableFuture
                .supplyAsync(() -> {
                    account.setId(id);
                    return accountService.update(account);
                })
                .exceptionally(e -> {
                    throw (RuntimeException) e.getCause();
                });
    }

    @DeleteMapping(value = "/{id}")
    public Future<Void> deleteOne(@PathVariable long id) {
        return CompletableFuture.runAsync(() -> accountService.deleteById(id));
    }


    @GetMapping
    public Future<Iterable<Account>> findAll() {
        return CompletableFuture.supplyAsync(accountService::findAll);
    }

    @DeleteMapping
    public Future<Void> deleteAll() {
        return CompletableFuture.runAsync(accountService::deleteAll);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<Object> handleAll(AccountNotFoundException e, WebRequest request) {
        return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
