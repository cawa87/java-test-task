package ru.smarty.accountapi.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.smarty.accountapi.exceptions.BadRequest;
import ru.smarty.accountapi.exceptions.NotFound;
import ru.smarty.accountapi.models.Account;
import ru.smarty.accountapi.repositories.AccountRepository;
import ru.smarty.accountapi.services.AccountDetailService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private AccountRepository accountRepository;
    private AccountDetailService accountDetailService;
    private PasswordEncoder passwordEncoder;

    public AccountController(AccountRepository accountRepository, AccountDetailService accountDetailService, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.accountDetailService = accountDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Account> accounts() {
        return accountRepository.findAll(new Sort("id"));
    }

    @PostMapping
    @Transactional
    public Account create(@RequestBody Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequest(bindingResult.getAllErrors().toString());
        }

        try {
            account.setAccountDetails(null);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account = accountRepository.save(account);
            account.setAccountDetails(accountDetailService.getAccountDetails(account.getId()));
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException unique) {
            throw new BadRequest("Login is not unique.");
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public Account update(@PathVariable("id") int accountId, @RequestBody Account update, BindingResult bindingResult) {
        Account account = findAccountOr404(accountId);

        if (bindingResult.hasErrors()) {
            throw new BadRequest(bindingResult.getAllErrors().toString());
        }

        // Boring, but bullet-proof. More advanced techniques to come in case of large volume of these.
        account.setLogin(update.getLogin());
        account.setPersonalInfo(update.getPersonalInfo());
        if (!StringUtils.isEmpty(update.getPassword())) {
            account.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        try {
            return accountRepository.save(account);
        } catch (DataIntegrityViolationException unique) {
            throw new BadRequest("Login is not unique.");
        }
    }

    @GetMapping("/{id}")
    public Account get(@PathVariable("id") int accountId) {
        return findAccountOr404(accountId);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") int accountId) {
        Account account = findAccountOr404(accountId);
        accountRepository.delete(account);
    }

    private Account findAccountOr404(@PathVariable("id") int accountId) {
        Account account = accountRepository.getOne(accountId);
        if (account == null) {
            throw new NotFound("Can't find account with id " + accountId);
        }
        return account;
    }
}
