package com.helospark.lightdi.annotation;

import static com.helospark.lightdi.properties.Environment.DEFAULT_PROPERTY_ORDER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(PropertySources.class)
public @interface PropertySource {
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String ORDER_ATTRIBUTE_NAME = "order";
    public static final String IGNORE_RESOURCE_NOT_FOUND_ATTRIBUTE_NAME = "ignoreResourceNotFound";

    public String[] value();

    public int order() default DEFAULT_PROPERTY_ORDER;

    public boolean ignoreResourceNotFound() default false;
}
