package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Create the annotated bean only if the given class is on the classpath.<br>
 * Great for fallback for conditional features that is created if there is a missing dependency for the application.<br>
 * 
 * For example:
 * <pre>
{@literal @}Configuration
public void ContextConfiguration {
  {@literal @}Bean
  {@literal @}ConditionalOnMissingClass("com.helospark.SomeClassFromAnotherJarImplementingFirstBeanInterface")
  public FirstBean firstBean() {
    return new SimpleFallbackImplementationOfFirstBean();
  }
  
}
</pre>
 * <p>
 * Works with {@literal @}Bean and stereotype annotations.<br>
 * If you are writing a library with this annotation, you probably want to add the dependency that contains that class as optional dependency in Maven.
 * In that case, the user of the library can toggle the feature by including a class in the classpath.
 *   
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ConditionalOnMissingClass {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return Fully qualified name of the class to check.<br>
     * If the class is missing, then the annotated bean will be created.
     */
    public String value();

}
