package ru.asmirnov.accservice.dto;

import java.util.Objects;

/**
 * @author Alexey Smirnov at 28/03/2018
 */
public class AccountDetailsResponseDto {

    private Long id;
    private String accountDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(String accountDetails) {
        this.accountDetails = accountDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetailsResponseDto that = (AccountDetailsResponseDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(accountDetails, that.accountDetails);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, accountDetails);
    }
}
