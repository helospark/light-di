package com.helospark.lightdi.exception;

/**
 * Exception indicating that the configuration of the context caused a failure.
 * @author helospark
 */
public class IllegalConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 341215079528854509L;

    public IllegalConfigurationException(String cause) {
        super(cause);
    }

    public IllegalConfigurationException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

}
