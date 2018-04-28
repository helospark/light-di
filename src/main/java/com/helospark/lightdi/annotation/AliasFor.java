package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to set two attributes of annotation(s) to mean the same thing.
 * <p>
 * For this to get processed, the annotation must be processed by {@link com.helospark.lightdi.util.AnnotationUtil AnnotationUtil}.
 * All annotation within the LightDi framework are processed by that class. 
 * <p>
 * There are two common uses:
 * <ul>
 * <li>Set a field of the meta annotation, for example:
 * <pre>
{@literal @}Retention(RUNTIME)
{@literal @}PropertySource
public {@literal @}interface TestPropertySource {
    {@literal @}AliasFor(annotation = PropertySource.class, value = "value")
    String[] locations() default {};
}
  </pre>
 * <i>In this case setting the location will set the PropertySource metaannotation's value field.</i>
 * </li>
 * <li>Create an alias between two attributes:
 * <pre>
public {@literal @}interface SomeAnnotation {
  {@literal @}AliasFor("name")
  String value() default "";
  {@literal @}AliasFor("value")
  String name() default "";
}
  </pre>
 * <i>In this case setting value has the same effect as setting name.</i>
 * </li>
 * </ul>
 * <p>
 * There are couple of constraints:<br>
 *  - Type must be equal<br>
 *  - Default value must be the same<br>
 *  - On the same object the alias must be in both direction (both fields must be annotation with {@literal @}AliasFor pointing to each other.<br>
 *  - When overriding meta annotation attribute, that attribute must exists, and will be overridden for all annotations with the same type<br>
 *  
 * @author helospark
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface AliasFor {

    /**
     * @return Attribute name to define the alias for.
     */
    String value();

    /**
     * @return If you would like to override meta annotation field, set the annotation class type.
     * Type of Annotation means, that the current annotation is used.
     */
    Class<? extends Annotation> annotation() default Annotation.class;

}
