package io.ronte.exception;

public class ExceptionUtils {

    public static Object handleFutureException(Throwable t) {
        Throwable cause = t.getCause();
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        return null;
    }
}
