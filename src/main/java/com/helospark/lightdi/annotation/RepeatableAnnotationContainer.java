package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates the the annotated annotation is a container for other annotation to make them repeatable.
 * <p>
 * In order for this annotation to have effect the annotation must be processed by {@link com.helospark.lightdi.util.AnnotationUtil AnnotationUtil}.<br>
 * If used the container itself will be completely invisible in the result, only the contained annotations are returned.
 * <br>
 * Annotated annotation must have a value attribute returning a list of annotations.
 * <br>
 * See {@link com.helospark.lightdi.annotation.PropertySources PropertySources} for an example.
 * @author helospark
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface RepeatableAnnotationContainer {

}
