package com.helospark.lightdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that the given class is eligible for ComponentScan.
 * <p>
 * If there is a ComponentScan that has the value of the same or super package of the annotated class,
 * then the annotated class will be instantiated, it's dependencies resolved and an instance is injected into the LightDi context.
 * <p>
 * It is just one of the stereotype annotations, it has the same exact same effect as {@link com.helospark.lightdi.annotation.Component Component} and {@link com.helospark.lightdi.annotation.Configuration Configuration}.
 * <p>
 * If any of the Autowired dependencies fail to inject, or exception is thrown during setter or constructor call, LightDi context init fail.
 * <p>
 * You can set the bean name by the value attribute of the annotation.
 * If not given the name of the bean will be generated from the classname, by turning the class's first letter to lower case.
 * 
 * @author helospark
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    public static final String NAME_ATTRIBUTE_NAME = "value";

    /**
     * @return Name of the bean.
     * If not given, it will be generated from the classname by setting the first letter to lowerCase.
     */
    String value() default "";
}
