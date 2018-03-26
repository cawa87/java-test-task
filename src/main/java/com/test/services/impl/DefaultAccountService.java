package com.test.services.impl;

import com.test.entities.Account;
import com.test.repositories.AccountRepository;
import com.test.services.AccountDetailsService;
import com.test.services.AccountService;
import com.test.services.FileService;
import com.test.services.base.impl.DefaultBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DefaultAccountService extends DefaultBaseService<Account> implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountDetailsService accountDetailsService;
    private final FileService fileService;

    @Autowired
    public DefaultAccountService(AccountRepository accountRepository,
                                 AccountDetailsService accountDetailsService,
                                 FileService fileService) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.accountDetailsService = accountDetailsService;
        this.fileService = fileService;
    }

    @Override
    public Optional<Account> getByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account get(Long id) {
        Account account = super.get(id);
        if (null == account.getAccountDetails()) {
            String accountDetails = accountDetailsService.getAccountDetails(id);
            account.setAccountDetails(accountDetails);
            update(account);
        }
        return account;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Account account = get(id);
        fileService.delete(account.getFiles());
        super.delete(id);
    }
}
