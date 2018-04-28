package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Sets the order of beans.
 * <p>
 * Primary cases where order matters:
 * <ul>
 *   <li>
 *   Injecting a list of beans of a certain interface. The order of the beans will take the {@literal @}Order annotation into consideration.
 *   </li>
 *   <li>
 *   In case of multiple {@literal @}Primary beans, the one with highest order will be used.
 *   </li>
 * </ul>
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Order {
    public static final String ORDER_ATTRIBUTE_NAME = "value";

    /**
     * @return the order. Lower value means higher priority.
     */
    public int value();

}
