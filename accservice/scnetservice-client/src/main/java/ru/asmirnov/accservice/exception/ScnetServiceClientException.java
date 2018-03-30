package ru.asmirnov.accservice.exception;

/**
 * @author Alexey Smirnov at 29/03/2018
 */
public class ScnetServiceClientException extends Exception {

    public ScnetServiceClientException() {
    }

    public ScnetServiceClientException(String message) {
        super(message);
    }

    public ScnetServiceClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScnetServiceClientException(Throwable cause) {
        super(cause);
    }

    public ScnetServiceClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
