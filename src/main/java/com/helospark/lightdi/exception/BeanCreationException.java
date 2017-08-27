package com.helospark.lightdi.exception;

public class BeanCreationException extends RuntimeException {

    public BeanCreationException(String message, Exception cause) {
        super(message, cause);
    }

}
