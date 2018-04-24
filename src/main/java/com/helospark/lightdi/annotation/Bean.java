package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Bean {
    public static final String VALUE_ATTRIBUTE_NAME = "value";

    /**
     * Bean name. Alias for name().
     * @return bean name
     */
    @AliasFor("name")
    public String value() default "";

    /**
     * Bean name. Alias for value().
     * @return bean name
     */
    @AliasFor("value")
    public String name() default "";
}
