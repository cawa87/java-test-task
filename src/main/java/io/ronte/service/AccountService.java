package io.ronte.service;

import io.ronte.model.Account;

public interface AccountService {
    Account save(Account account);

    Account update(Account account);

    Account findById(long id);

    Iterable<Account> findAll();

    void deleteById(long id);

    void deleteAll();
}
