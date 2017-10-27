package io.ronte.service;

import io.ronte.model.Account;

import java.util.Optional;

public interface AccountService {
    Account save(Account account);

    Account update(Account account);

    Optional<Account> findById(long id);

    Iterable<Account> findAll();

    void deleteById(long id);

    void deleteAll();
}
