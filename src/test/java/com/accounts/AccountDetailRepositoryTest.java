package com.accounts;

import com.accounts.api.model.Account;
import com.accounts.api.model.AccountDetail;
import com.accounts.dao.AccountDetailRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by pasha on 15.02.18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountDetailRepositoryTest {
    @Autowired
    AccountDetailRepository accountDetailRepository;

    @Test
    public void testAccountDetailRep() {
        Account account = new Account(1L, "");
        account.setId(1L);
        AccountDetail detail = accountDetailRepository.readAccountDetail(account);
        Assert.assertEquals((long)detail.getId(), 1L);
    }
}
