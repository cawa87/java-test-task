package com.test.services;

import com.test.entities.Account;
import com.test.services.base.BaseService;

import java.util.Optional;

public interface AccountService extends BaseService<Account> {
    Optional<Account> getByUsername(String username);
}
