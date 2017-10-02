package com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest;

/**
 * For simple testing purposes.
 * Similar to slf4j logger.
 * @author helospark
 */
public class Slf4jStubLogger {
    private String name;

    public Slf4jStubLogger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
