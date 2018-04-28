package com.helospark.lightdi.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to indicate to automatically discover beans from the current application context to inject into the annotation field/method/parameter.
 * <p>
 * Example usage:
 * <pre>
{@literal @}Autowired
public SomeClass someClass;
</pre>
 * In this case an LightDi search for a registered bean with SomeClass type and inject into the field. If none or multiple found, context startup will fail.
 * <p>
 * In case you would like to choose from multiple instances, you can use {@link com.helospark.lightdi.annotation.Qualifier Qualifier}.<br>
 * In case you would like to keep the instance null in case none found, you can set the required attribute in this annotation to false.
 * <p>
 * You can attach this annotation to at most 1 constructor. Adding this annotation to a constructor is not required, if there is only one.
 * 
 * @author helospark
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    public static final String REQUIRED_ATTRIBUTE_NAME = "required";

    public boolean required() default true;

}
