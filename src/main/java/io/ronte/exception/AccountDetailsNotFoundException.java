package io.ronte.exception;

public class AccountDetailsNotFoundException extends RuntimeException {

    public AccountDetailsNotFoundException(long id, Throwable t) {
        super("Failed to find account details with id=" + id, t);
    }
}
