package com.test.common.exceptions.base;

public class NonPersistedAccountUpdateException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "An attempt to update non-persisted yet account.";

    public NonPersistedAccountUpdateException() {
        super(DEFAULT_MESSAGE);
    }
}
