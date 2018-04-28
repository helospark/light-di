package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Import a stereotype annotated class to the context.
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Import {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return Class to import.
     */
    Class<?>[] value();

}
