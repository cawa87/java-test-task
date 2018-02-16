package com.accounts;

import com.accounts.api.model.Account;
import com.accounts.service.AccountService;
import com.accounts.web.AccountController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.util.Arrays;
import java.util.List;

/**
 * Created by pasha on 16.02.18.
 */
//@RunWith(SpringRunner.class)
public class AccountRestTest {

    AccountService accountService;

    AccountController accountController;
    List<Account> allAccount = Arrays.asList(new Account(1L,"account1"), new Account(2L,"account2"));
    Account newOne = new Account(3L,"account3");

    @Before
    public void setUp() {
        accountService = Mockito.mock(AccountService.class);
        accountController = new AccountController(accountService);
        Mockito.when(accountService.findAll()).thenReturn(allAccount);
        Mockito.when(accountService.save(newOne)).thenReturn(newOne);
    }


    @Test
    public void testAccountRetreiveAll() {
        List<Account> accounts = accountController.retrieveAllAccounts();
        Assert.assertEquals(accounts.size(), 2);
    }


}
