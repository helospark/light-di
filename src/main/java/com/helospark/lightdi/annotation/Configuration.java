package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Configuration {
    public static final String NAME_ATTRIBUTE_NAME = "value";

    public String value() default "";
}
