package io.ronte.service;

import io.ronte.exception.AccountNotFoundException;
import io.ronte.model.Account;
import io.ronte.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {

        Account accountToUpdate = accountRepository.findOne(account.getId());

        if (accountToUpdate == null) {
            throw new AccountNotFoundException(account.getId());
        }

        accountToUpdate.setLogin(account.getLogin());
        accountToUpdate.setEmail(account.getEmail());
        accountToUpdate.setName(account.getName());

        return accountRepository.save(accountToUpdate);
    }

    @Override
    public Optional<Account> findById(long id) {
        Account account = accountRepository.findOne(id);
        return Optional.ofNullable(account);
    }

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        try {
            accountRepository.delete(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountNotFoundException(id);
        }
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
