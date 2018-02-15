package com.accounts.service;

/**
 * Created by pasha on 14.02.18.
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String s) {
        super(s);
    }
}
