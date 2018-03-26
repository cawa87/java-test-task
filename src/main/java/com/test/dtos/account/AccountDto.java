package com.test.dtos.account;

import com.test.entities.Account;
import com.test.services.RoleService;

import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {

    private Long id;

    private String username;

    private String accountDetails;

    private List<String> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(String accountDetails) {
        this.accountDetails = accountDetails;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Account toEntity() {
        Account account = new Account();
        account.setId(getId());
        account.setUsername(getUsername());
        account.setAccountDetails(getAccountDetails());
        return account;
    }

    public Account toEntity(RoleService roleService) {
        Account account = toEntity();
        account.setRoles(getRoles().stream().map(roleService::getByName).collect(Collectors.toList()));
        return account;
    }
}
