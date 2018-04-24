package com.helospark.lightdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    public static final String REQUIRED_ATTRIBUTE_NAME = "required";

    public boolean required() default true;

}
