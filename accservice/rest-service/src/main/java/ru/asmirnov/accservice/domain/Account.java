package ru.asmirnov.accservice.domain;

import java.util.Objects;

/**
 * @author Alexey Smirnov at 27/03/2018
 */
public class Account {

    private Long id;
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
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(number, account.number) &&
                Objects.equals(accountDetails, account.accountDetails);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, number, accountDetails);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", accountDetails='" + accountDetails + '\'' +
                '}';
    }

    public static final class AccountBuilder {
        private Long id;
        private String number;
        private String accountDetails;

        private AccountBuilder() {
        }

        public static AccountBuilder anAccount() {
            return new AccountBuilder();
        }

        public AccountBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public AccountBuilder withNumber(String number) {
            this.number = number;
            return this;
        }

        public AccountBuilder withAccountDetails(String accountDetails) {
            this.accountDetails = accountDetails;
            return this;
        }

        public Account build() {
            Account account = new Account();
            account.setId(id);
            account.setNumber(number);
            account.setAccountDetails(accountDetails);
            return account;
        }
    }
}
