package io.ronte.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(long id) {
        super("Failed to find account with id=" + id);
    }
}
