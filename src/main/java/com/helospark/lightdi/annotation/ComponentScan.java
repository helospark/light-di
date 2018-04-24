package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ComponentScan {
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackageClasses";
    public static final String ONLY_SCAN_THIS_JAR_ATTRIBUTE_NAME = "onlyScanThisJar";

    public String[] value() default {};

    public Class<?>[] basePackageClasses() default {};

    public boolean onlyScanThisJar() default true;
}
