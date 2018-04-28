package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotated method's returned value will be injected as bean to the current application context, therefore it will be a eligible for Autowire and injection.
 * <p>
 * Example:
 * <pre>
{@literal @}Configuration
public class ConfigurationClass {
  {@literal @}Bean
  public SomeClass someClass() {
    return new SomeClass();
  }
}
</pre>
 * In this case LightDi context will contain a bean called "someClass" with type SomeClass.
 * <p>
 * If the annotated method throws, context init will fail.<br>
 * The annotated method must be in a LightDi managed bean.<br>
 * Name of the bean can be changed using the name attribute.
 *  
 * @author helospark
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Bean {
    public static final String VALUE_ATTRIBUTE_NAME = "value";

    /**
     * @return Name of the bean.
     * If not defined method name will be the bean name.
     * Alias for name().
     */
    @AliasFor("name")
    public String value() default "";

    /**
     * @return Bean name.
     * If not defined method name will be the bean name.
     * Alias for value().
     */
    @AliasFor("value")
    public String name() default "";
}
