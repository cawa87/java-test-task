package com.accounts.service;

import com.accounts.dao.AccountDetailRepository;
import com.accounts.dao.AccountRepository;
import com.accounts.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by pasha on 14.02.18.
 */
@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountDetailRepository accountDetailRepository;

    public List<Account> findAll() {
        List<Account> result = accountRepository.findAll();

        return result;
    }

    public Optional<Account> findById(long id) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findOne(id));
        if (account.isPresent()) {
            account.get().setAccountDetail(accountDetailRepository.readAccountDetail(account.get()));
        }

        return account;
    }

    public void deleteById(long id) {
        accountRepository.delete(id);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
