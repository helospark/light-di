package com.helospark.lightdi.annotation;

import static com.helospark.lightdi.properties.Environment.DEFAULT_PROPERTY_ORDER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Loads properties from file on the classpath or filesystem.
 * <p>
 * Loaded properties can be injected using {@literal @}Value("${PropertyName}").<br>
 * If the value starts with "classpath:" the file will be searched in the classpath, if value starts with "file:" it will load from filesystem.
 * <p>
 * Property file should have the standard format of property file:
 * <pre>
PropertyName=PropertyValue
AnotherName=AnotherValue
</pre>
 * Properties are allowed to use other properties.
 * <br>
 * value is allowed to use properties, for example:
<pre>
{@literal @}PropertySource("classpath:application-${ENVIRONMENT}.properties")
{@literal @}Configuration
public class AppConfig {
}
</pre>
 * Properties are resolved by priority, highest priority will be loaded first, with this you can have default and environment specific property. For example:
 * <pre>
{@literal @}PropertySource(value = "file:/etc/application-config.properties", order = -2, ignoreResourceNotFound = true)
{@literal @}PropertySource(value = "classpath:application-${ENVIRONMENT}.properties", order = -1, ignoreResourceNotFound = true)
{@literal @}PropertySource(value = "classpath:application-default.properties", order = 0)
{@literal @}Configuration
public class AppConfig {
}
 * </pre>
 * In this case when a property is resolved, first the highest precedence file at /etc/application-config.properties is checked, then environment specific
 * file at application-${ENVIRONMENT}.properties (ENVIRONMENT should be an system property to the program), finally application-default.properties file
 * is checked.
 * <p>
 * In Java 8 you can repeat the annotation, pre Java 8 you can use the annotation container {@link com.helospark.lightdi.annotation.PropertySources PropertySources}.
 * 
 * @author helospark
 */
@Retention(RUNTIME)
@Target(TYPE)
@Repeatable(PropertySources.class)
public @interface PropertySource {
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String ORDER_ATTRIBUTE_NAME = "order";
    public static final String IGNORE_RESOURCE_NOT_FOUND_ATTRIBUTE_NAME = "ignoreResourceNotFound";

    /**
     * @return Property file location.<br>
     * If the value starts with "classpath:" the file will be searched in the classpath, if value starts with "file:" it will load from filesystem.
     */
    public String[] value();

    /**
     * @return Order of the property source. Lower values mean higher precedence.<br>
     * Values should be between @link com.helospark.lightdi.properties.Environment.LOWEST_PROPERTY_ORDER and com.helospark.lightdi.properties.Environment.HIGHEST_PROPERTY_ORDER.<br>
     * Default value is 0.
     */
    public int order() default DEFAULT_PROPERTY_ORDER;

    /**
     * @return Whether to fail application start if the property file is missing. By default context load fail on missing file.
     */
    public boolean ignoreResourceNotFound() default false;
}
