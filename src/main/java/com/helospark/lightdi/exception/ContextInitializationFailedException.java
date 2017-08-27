package com.helospark.lightdi.exception;

public class ContextInitializationFailedException extends RuntimeException {

    public ContextInitializationFailedException(String message, Exception cause) {
        super(message, cause);
    }

}
