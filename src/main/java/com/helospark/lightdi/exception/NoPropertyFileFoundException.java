package com.helospark.lightdi.exception;

/**
 * Exception indicates that a property file cannot be loaded.
 * @author helospark
 *
 */
public class NoPropertyFileFoundException extends RuntimeException {
    private static final long serialVersionUID = 5666985711686994069L;

    public NoPropertyFileFoundException(String detailMessage) {
        super(detailMessage);
    }

}
