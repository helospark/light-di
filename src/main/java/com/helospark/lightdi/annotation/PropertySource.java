package com.helospark.lightdi.annotation;

import static com.helospark.lightdi.properties.Environment.DEFAULT_PROPERTY_ORDER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
public @interface PropertySource {
    public String[] value();

    public int order() default DEFAULT_PROPERTY_ORDER;
}
