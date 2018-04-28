package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Inject a string or property value to the annotated field.
 * <p>
 * To resolve a property with name TEST_PROPERTY you must use {@literal @}Value("${TEST_PROPERTY}").<br>
 * To inject a String you can just use a String value {@literal @}Value("VALUE"), this will inject the string "VALUE".<br>
 * You can use both, for example {@literal @}Value("value is ${TEST_PROPERTY}"), this will resolve the value with ${TEST_PROPERTY} but leave string untouched.
 * <p>
 * Value may also need to be converted to the correct type, for example:
 * <pre>
{@literal @}Value("${INT_PROPERTY}")
private Integer intValue;
</pre>
 * This will use the built-in IntegerPropertyConverter to convert the given String to Integer. In case it cannot be converter
 * context startup fails.<br>
 * You can define custom converter for your type by implementing {@link com.helospark.lightdi.properties.PropertyConverter PropertyConverter}
 * 
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER, METHOD })
public @interface Value {
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String REQUIRED_ATTRIBUTE_NAME = "required";

    /**
     * @return Value to resolve. Can contain both string values and property references in ${} construct.
     */
    public String value();

    /**
     * @return whether to fail the context if the property cannot be resolved.
     * <br>If this value is false and the value cannot be resolved, empty string is injected.
     */
    public boolean required() default true;
}
