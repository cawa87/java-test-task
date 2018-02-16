package com.accounts.dao;

import com.accounts.api.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pasha on 14.02.18.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
