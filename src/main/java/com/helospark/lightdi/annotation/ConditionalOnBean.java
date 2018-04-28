package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Create the annotated bean only if the other given bean exists.
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
  {@literal @}ConditionalOnBean(FirstBean.class)
  public SecondBean secondBean() {
    return new SecondBean();
  }
  
}
</pre>
 * In this case SecondBean will be created, because the first bean is registered.<br>
 * If the FirstBean was not registered, neither will the SecondBean.
 * <p>
 * Works with {@literal @}Bean and stereotype annotations.<br>
 * Edge case is when two beans with ConditionalOnBean point to each other. In that case both will be created. 
 *   
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface ConditionalOnBean {
    public static final String ATTRIBUTE_NAME = "value";

    /**
     * @return Bean type to check. If the given bean exists, this one will be created too.
     */
    public Class<?> value();

}
