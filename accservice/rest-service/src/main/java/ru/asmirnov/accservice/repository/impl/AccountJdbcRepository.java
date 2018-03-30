package ru.asmirnov.accservice.repository.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.incrementer.PostgreSQLSequenceMaxValueIncrementer;
import org.springframework.stereotype.Repository;
import ru.asmirnov.accservice.domain.Account;
import ru.asmirnov.accservice.repository.AccountRepository;

import java.util.List;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
@Repository
public class AccountJdbcRepository implements AccountRepository {

    public static final String ACCOUNT_SEQ = "account_seq";
    public static final RowMapper<Account> ACCOUNT_ROW_MAPPER = (resultSet, i) -> Account.AccountBuilder.anAccount()
            .withId(resultSet.getLong(1))
            .withNumber(resultSet.getString(2))
            .withAccountDetails(resultSet.getString(3))
            .build();

    private final JdbcTemplate jdbcTemplate;
    private final PostgreSQLSequenceMaxValueIncrementer sequence;

    public AccountJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.sequence = new PostgreSQLSequenceMaxValueIncrementer(jdbcTemplate.getDataSource(), ACCOUNT_SEQ);
    }

    @Override
    public Long generateId() {
        return sequence.nextLongValue();
    }

    @Override
    public Account add(Account account) {
        jdbcTemplate.update("insert into account (id, number, account_details) VALUES (?, ?, ?)",
                account.getId(), account.getNumber(), account.getAccountDetails());
        return account;
    }

    @Override
    public Account update(Account account) {
        jdbcTemplate.update("update account set account_details = ?, number = ? where id = ?",
                account.getAccountDetails(), account.getNumber(), account.getId());
        return account;
    }

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("select id, number, account_details from account", ACCOUNT_ROW_MAPPER);
    }

    @Override
    public Account findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select id, number, account_details from account where id = ?",
                    ACCOUNT_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void removeById(Long id) {
        jdbcTemplate.update("delete from account where id = ?", id);
    }
}
