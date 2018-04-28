package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotated bean will not be automatically instantiated when context starts.
 * <p>
 * Normally all beans are lazy in LightDi, and only eagerly load the first time they are referenced. 
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Lazy {

}
