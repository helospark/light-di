package com.helospark.lightdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
    public static final String NAME_ATTRIBUTE_NAME = "value";

    String value() default "";
}
