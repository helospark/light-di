package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Create the annotated bean only if the given property has the given value.<br>
 * Great for conditional features that can be toggled by property file or system property.<br>
 * 
 * For example:
 * <pre>
{@literal @}Configuration
public void ContextConfiguration {
  {@literal @}Bean
  {@literal @}ConditionalProperty(property = "activeCoolFeature", havingValue="true")
  public CoolFeature coolFeature() {
    return new CoolFeature();
  }
  
}
</pre>
 * <p>
 * Works with {@literal @}Bean and stereotype annotations.<br>
 *   
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ConditionalOnProperty {
    public static final String PROPERTY_ATTRIBUTE_NAME = "property";
    public static final String HAVING_VALUE_ATTRIBUTE_NAME = "havingValue";

    /**
     * @return Property to check. This must exist.
     */
    public String property();

    /**
     * @return Value of the property.
     * If the given property has this value, the annotated bean will be created.
     */
    public String havingValue();
}
