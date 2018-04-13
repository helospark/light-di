package com.helospark.lightdi.dependencywire.chain.support.domain;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

/**
 * Similar to Java 8 AnnotatedType, but compatible with Java 7.
 * @author helospark
 */
public class CustomAnnotatedElement implements AnnotatedElement {
    private Annotation[] annotations;

    public CustomAnnotatedElement(Annotation[] annotations) {
        this.annotations = annotations;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> arg0) {
        return (T) Arrays.stream(annotations)
                .filter(annotation -> annotation.annotationType().equals(arg0))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Annotation[] getAnnotations() {
        return annotations;
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return annotations;
    }

}
