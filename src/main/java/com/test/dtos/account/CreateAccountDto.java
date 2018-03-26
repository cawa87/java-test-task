package com.test.dtos.account;

import com.test.entities.Account;

public class CreateAccountDto {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account toEntity() {
        Account account = new Account();
        account.setUsername(getUsername());
        return account;
    }
}
