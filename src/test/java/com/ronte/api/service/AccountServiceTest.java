package com.ronte.api.service;

import com.ronte.api.dao.AccountDAO;
import com.ronte.api.entity.Account;
import com.ronte.api.exception.ResourceAlreadyExistsException;
import com.ronte.api.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;

    @Mock
    private ExternalAccountDetailsService externalAccountDetailsService;

    private AccountService underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new AccountService(accountDAO, externalAccountDetailsService);
    }

    @Test
    public void shouldReturnAccounts() throws Exception {
        when(accountDAO.findAll()).thenReturn(Collections.singletonList(new Account()));

        List<Account> actual = underTest.getAccounts();

        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(1));
    }

    @Test
    public void shouldReturnAccount() throws Exception {
        Account expected = new Account();
        when(accountDAO.findById(1)).thenReturn(expected);

        Account actual = underTest.getAccount(1);

        assertThat(actual, is(expected));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void shouldThrowExceptionWhenAccountIsNotFound() throws Exception {
        when(accountDAO.findById(1)).thenReturn(null);

        underTest.getAccount(1);
    }

    @Test
    public void shouldAddAccount() throws Exception {
        Account account = new Account();

        underTest.addAccount(account);

        verify(accountDAO).save(refEq(account));
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void shouldThrowExceptionWhenAccountAlreadyExists() throws Exception {
        Account account = new Account();
        doThrow(new DataIntegrityViolationException("test")).when(accountDAO).save(any());

        underTest.addAccount(account);
    }

    @Test
    public void shouldUpdateAccount() throws Exception {
        Account oldAccount = new Account();
        oldAccount.setId(1);
        oldAccount.setLogin("old");
        oldAccount.setPassword("old");
        oldAccount.setFullName("old");
        when(accountDAO.findById(1)).thenReturn(oldAccount);

        Account newAccount = new Account();
        newAccount.setLogin("new");
        newAccount.setPassword("new");
        newAccount.setFullName("new");

        underTest.updateAccount(1, newAccount);

        assertThat(oldAccount.getLogin(), is("new"));
        assertThat(oldAccount.getPassword(), is("new"));
        assertThat(oldAccount.getFullName(), is("new"));
    }

    @Test
    public void shouldDeleteAccount() throws Exception {
        underTest.deleteAccount(1);

        verify(accountDAO).delete(1);
    }

    @Test
    public void shouldReturnAccountDetails() throws Exception {
        String expected = "test";
        Account account = new Account();
        account.setId(1);
        when(accountDAO.findById(1)).thenReturn(account);
        when(externalAccountDetailsService.getDetails(1)).thenReturn(expected);

        String actual = underTest.getAccountDetails(1);

        assertThat(actual, is(expected));
    }
}
