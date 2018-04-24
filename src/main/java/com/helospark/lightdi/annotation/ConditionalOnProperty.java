package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ConditionalOnProperty {
    public static final String PROPERTY_ATTRIBUTE_NAME = "property";
    public static final String HAVING_VALUE_ATTRIBUTE_NAME = "havingValue";

    public String property();

    public String havingValue();
}
