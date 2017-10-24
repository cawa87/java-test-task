package com.example.model;

/**
 * @author dasolovyov on 23.10.2017.
 */
public class AccountDetailsResponse {
    private Integer id;
    private String accountDetails;

    public AccountDetailsResponse() {
    }

    public AccountDetailsResponse(Integer id, String accountDetails) {
        this.id = id;
        this.accountDetails = accountDetails;
    }

    public AccountDetailsResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(String accountDetails) {
        this.accountDetails = accountDetails;
    }
}
