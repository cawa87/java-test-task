package ru.asmirnov.accservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.asmirnov.accservice.ScnetServiceClient;
import ru.asmirnov.accservice.domain.Account;
import ru.asmirnov.accservice.dto.AccountDetailsRequestDto;
import ru.asmirnov.accservice.dto.AccountDetailsResponseDto;
import ru.asmirnov.accservice.exception.ScnetServiceClientException;
import ru.asmirnov.accservice.mapper.AccountMapper;
import ru.asmirnov.accservice.repository.AccountRepository;
import ru.asmirnov.accservice.rest.dto.AccountDto;
import ru.asmirnov.accservice.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexey Smirnov at 27/03/2018
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ScnetServiceClient scnetServiceClient;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, ScnetServiceClient scnetServiceClient) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.scnetServiceClient = scnetServiceClient;
    }

    @Override
    public AccountDto save(AccountDto accountDto) throws ScnetServiceClientException {
        Account account = accountMapper.map(accountDto, Account.class);
        // if there is no id passed, then insert, otherwise update
        if (account.getId() == null) {
            // generate unique id
            Long id = accountRepository.generateId();

            // request account details
            AccountDetailsRequestDto accountDetailsRequestDto = new AccountDetailsRequestDto(id);
            AccountDetailsResponseDto accountDetails = scnetServiceClient.getAccountDetails(accountDetailsRequestDto);

            // setting generated above id and account detail
            account.setId(id);
            account.setAccountDetails(accountDetails.getAccountDetails());

            account = accountRepository.add(account);
        } else {
            account = accountRepository.update(account);
        }

        return accountMapper.map(account, AccountDto.class);
    }

    @Override
    public List<AccountDto> findAll() {
        List<Account> allAccounts = accountRepository.findAll();
        return allAccounts.stream()
                .map(account -> accountMapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(Long id) {
        Account account = accountRepository.findById(id);
        return account != null ? accountMapper.map(account, AccountDto.class) : null;
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.removeById(id);
    }
}
