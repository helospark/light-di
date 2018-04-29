package com.helospark.lightdi.exception;

/**
 * Exception indicating that the context start failed.
 * <p>
 * Cause will always contain the original exception that was the reason for the failure.
 * @author helospark
 */
public class ContextInitializationFailedException extends RuntimeException {
    private static final long serialVersionUID = 528216792095343945L;

    public ContextInitializationFailedException(String message, Exception cause) {
        super(message, cause);
    }

}
