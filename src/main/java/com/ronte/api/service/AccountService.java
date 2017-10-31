package com.ronte.api.service;

import com.ronte.api.dao.AccountDAO;
import com.ronte.api.entity.Account;
import com.ronte.api.exception.ResourceAlreadyExistsException;
import com.ronte.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private final AccountDAO accountDAO;
    private final ExternalAccountDetailsService externalAccountDetailsService;

    @Autowired
    public AccountService(AccountDAO accountDAO, ExternalAccountDetailsService externalAccountDetailsService) {
        this.accountDAO = accountDAO;
        this.externalAccountDetailsService = externalAccountDetailsService;
    }

    public List<Account> getAccounts() {
        return accountDAO.findAll();
    }

    @Cacheable("account")
    public Account getAccount(long id) {
        return findOrThrow(id);
    }

    public void addAccount(Account account) {
        try {
            accountDAO.save(account);
        } catch (EntityExistsException | DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException();
        }
    }

    @CachePut(value = "account", key = "#id")
    public void updateAccount(long id, Account account) {
        Account dbAccount = findOrThrow(id);

        dbAccount.setLogin(account.getLogin());
        dbAccount.setPassword(account.getPassword());
        dbAccount.setFullName(account.getFullName());

        accountDAO.flush();
    }

    @CacheEvict(value = "account", key = "#id")
    public void deleteAccount(long id) {
        accountDAO.delete(id);
    }

    public String getAccountDetails(long id) {
        Account account = findOrThrow(id);

        return externalAccountDetailsService.getDetails(account.getId());
    }

    private Account findOrThrow(long id) {
        Account account = accountDAO.findById(id);
        if (account == null) {
            throw new ResourceNotFoundException();
        }

        return account;
    }
}
