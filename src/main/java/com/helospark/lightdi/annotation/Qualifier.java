package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Adds an additional bean-name constraint for Autowiring.
 * <p>
 * Useful in case multiple candidates are available. 
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ PARAMETER })
public @interface Qualifier {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return bean name to inject
     */
    public String value();
}
