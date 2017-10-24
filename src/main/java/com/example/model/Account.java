package com.example.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

/**
 * @author dasolovyov on 23.10.2017.
 */
@Entity
public class Account extends AbstractPersistable<Long> {
    private String email;
    private String name;
    private Integer accountDetailsId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAccountDetailsId() {
        return accountDetailsId;
    }

    public void setAccountDetailsId(Integer accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
    }
}
