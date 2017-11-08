package ru.smarty.accountapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smarty.accountapi.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByLogin(String login);
}
