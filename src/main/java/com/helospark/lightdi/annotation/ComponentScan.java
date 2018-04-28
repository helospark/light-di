package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation to indicate, that beans should be automatically discovered and created in and under the given package.
 * <p>
 * It find classes that has any of the stereotype annotations: {@link com.helospark.lightdi.annotation.Component Component}, {@link com.helospark.lightdi.annotation.Configuration Configuration} and {@link com.helospark.lightdi.annotation.Service Service}.
 * <br>
 * These classes are then injected to the LightDi context.
 * <p>
 * If no base package is given, the annotated class's base package is used.
 * Two ways to define package is to give a packageName to value attribute, or to given reference classes to basePackageClasses attribute.
 * 
 * @author helospark
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ComponentScan {
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackageClasses";

    /**
     * @return List of package names, like <b>com.helospark.components</b>.<p>
     * Generally less safe than basePackageClasses, since it is not checked by the compiler.
     */
    public String[] value() default {};

    /**
     * @return List of reference classes.
     * <p>
     * The package name will be extracted from these, and components will be discovered in and under their packages.<br>
     * Generally more safe than package, since it is checked by the compiler.
     */
    public Class<?>[] basePackageClasses() default {};

}
