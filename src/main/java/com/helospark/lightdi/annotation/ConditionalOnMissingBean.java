package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Create the annotated bean only if the other given bean does not exists.
 * <p>
 * Great to create fallback bean if the other does not exist.
 * 
 * For example:
 * <pre>
{@literal @}Configuration
public void ContextConfiguration {
  {@literal @}Bean
  public FirstBean firstBean() {
    return new FirstBean();
  }
  
  {@literal @}Bean
  {@literal @}ConditionalOnMissingBean(FirstBean.class)
  public SecondBean secondBean() {
    return new SecondBean();
  }
  
}
</pre>
 * In this case SecondBean will not be created, because the first bean is registered.<br>
 * If the FirstBean was not registered, the SecondBean would.
 * <p>
 * Works with {@literal @}Bean and stereotype annotations.<br>
 * Edge case is when two beans with ConditionalOnMissingBean point to each other. In that case none will be created. 
 *   
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ConditionalOnMissingBean {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return Other bean type to search for. If it does not exists then the current bean is created.
     */
    public Class<?> value();

}
