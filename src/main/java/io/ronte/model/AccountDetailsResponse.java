package io.ronte.model;

public class AccountDetailsResponse {

    private Long id;
    private String accountDetails;

    public AccountDetailsResponse() {
    }

    public AccountDetailsResponse(Long id, String accountDetails) {
        this.id = id;
        this.accountDetails = accountDetails;
    }

    public Long getId() {
        return id;
    }

    public String getAccountDetails() {
        return accountDetails;
    }
}
