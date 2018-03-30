package ru.asmirnov.accservice.rest.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author Alexey Smirnov at 27/03/2018
 */
public class AccountDto {

    private Long id;
    @NotNull
    @Digits(integer = 20, fraction = 0)
    @Size(min = 20, max = 20)
    private String number;
    private String accountDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        AccountDto that = (AccountDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(number, that.number) &&
                Objects.equals(accountDetails, that.accountDetails);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, accountDetails);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", accountDetails='" + accountDetails + '\'' +
                '}';
    }
}
