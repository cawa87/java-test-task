package ru.asmirnov.accservice.service;

import ru.asmirnov.accservice.exception.ScnetServiceClientException;
import ru.asmirnov.accservice.rest.dto.AccountDto;

import java.util.List;

/**
 * Service for work with accounts: add, add, delete, find
 *
 * @author Alexey Smirnov at 27/03/2018
 */
public interface AccountService {
    AccountDto save(AccountDto accountDto) throws ScnetServiceClientException;

    List<AccountDto> findAll();

    AccountDto getById(Long id);

    void deleteById(Long id);
}
