package com.example.model;

/**
 * @author dasolovyov on 23.10.2017.
 */
public class AccountDetailsRequest {
    private Integer id;

    public AccountDetailsRequest() {
    }

    public AccountDetailsRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
