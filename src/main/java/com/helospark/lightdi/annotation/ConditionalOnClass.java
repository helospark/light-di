package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Create the annotated bean only if the given class is on the classpath.<br>
 * Great for conditional features added only if the dependency is added in Maven.<br>
 * 
 * For example:
 * <pre>
{@literal @}Configuration
public void ContextConfiguration {
  {@literal @}Bean
  {@literal @}ConditionalOnClass("com.helospark.SomeClassFromAnotherJarImplementingFirstBeanInterface")
  public FirstBean firstBean() {
    return new SomeClassFromAnotherJarImplementingFirstBeanInterface();
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
public @interface ConditionalOnClass {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return Fully qualified name of the class.
     * If it is present on the classpath, the bean will be created.
     */
    public String value();

}
