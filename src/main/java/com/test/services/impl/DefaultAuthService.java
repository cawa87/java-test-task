package com.test.services.impl;

import com.test.entities.Account;
import com.test.services.AccountService;
import com.test.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthService implements AuthService {

    private final AccountService accountService;

    @Autowired
    public DefaultAuthService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Account> account = accountService.getByUsername(s);
        if (!account.isPresent()) {
            throw new UsernameNotFoundException("Account with username " + s + " does not exist.");
        }
        return account.get();
    }
}
