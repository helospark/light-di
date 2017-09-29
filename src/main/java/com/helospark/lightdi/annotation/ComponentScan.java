package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ComponentScan {

    public String[] value() default {};

    public Class<?>[] basePackageClasses() default {};

    public boolean onlyScanThisJar() default true;
}
