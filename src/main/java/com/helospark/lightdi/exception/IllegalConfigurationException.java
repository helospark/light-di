package com.helospark.lightdi.exception;

public class IllegalConfigurationException extends RuntimeException {

    public IllegalConfigurationException(String cause) {
        super(cause);
    }

    public IllegalConfigurationException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

}
