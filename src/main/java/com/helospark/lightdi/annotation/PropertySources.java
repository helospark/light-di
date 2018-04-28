package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Container for {@link PropertySource} annotations to make them repeatable.
 * @author helospark
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@RepeatableAnnotationContainer
public @interface PropertySources {

    /**
     * @return property sources
     */
    PropertySource[] value();
}
