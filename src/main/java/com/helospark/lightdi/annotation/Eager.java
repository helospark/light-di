package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotated bean will be automatically instantiated after context start, even if it has no reference.
 * <p>
 * Note that all it's dependency will be also instantiated as well as PostConstruct methods will be fired.
 * <br>
 * Normally all beans are lazy in LightDi, and only eagerly load the first time they are referenced. 
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Eager {

}
