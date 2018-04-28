package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Sets the scope for the bean.
 * <p>
 * Currently two scopes are supported:
 * <ul>
 *  <li>
 *  singleton: single instance is created per application context, always the same instance is injected
 *  </li>
 *  <li>
 *  prototype: every single injection will create a new instance
 *  </li>
 * </ul>
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Scope {
    public static final String SCOPE_ATTRIBUTE_NAME = "value";

    /**
     * @return the scope.<br>
     * You can use LightDiConstants.SCOPE_PROTOTYPE and LightDiConstants.SCOPE_SINGLETON.
     */
    public String value();
}
