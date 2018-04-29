package com.helospark.lightdi.exception;

/**
 * Exception indicating that a bean cannot be created.
 * @author helospark
 */
public class BeanCreationException extends RuntimeException {
    private static final long serialVersionUID = 4321191877669039437L;

    public BeanCreationException(String message, Exception cause) {
        super(message, cause);
    }

}
