package ru.asmirnov.accservice.repository;

import ru.asmirnov.accservice.domain.Account;

import java.util.List;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
public interface AccountRepository {

    Long generateId();

    Account add(Account account);

    Account update(Account account);

    List<Account> findAll();

    Account findById(Long id);

    void removeById(Long id);
}
