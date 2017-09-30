package com.helospark.lightdi.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.Test;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Service;

public class AnnotationUtilTest {

    @Test
    public void testGetMethodsAnnotatedWith() throws NoSuchMethodException, SecurityException {
        // GIVEN

        // WHEN
        List<Method> result = AnnotationUtil.getMethodsWithAnnotation(TestClassWithAnnotation.class,
                PostConstruct.class);

        // THEN
        assertThat(result, is(Collections.singletonList(TestClassWithAnnotation.class.getMethod("annotatedMethod"))));
    }

    @Test
    public void testGetAllMergedAnnotationsShouldReturnExpected() {
        // GIVEN

        // WHEN
        Set<Annotation> result = AnnotationUtil.getAllMergedAnnotations(AnnotatedClass.class);

        // THEN
        assertThat(getAnnotationEqualToClass(result, Component.class).size(), is(1));
        assertThat(getAnnotationEqualToClass(result, Service.class).size(), is(1));
        assertThat(getAnnotationEqualToClass(result, RepeatableAnnotations.class).size(), is(1));
        assertThat(getAnnotationEqualToClass(result, RepeatableAnnotations.class).get(0).value().length, is(2));
    }

    @Test
    public void testGetSingleAnnotationOfTypeShouldReturnExpected() {
        // GIVEN

        // WHEN
        Component component = AnnotationUtil.getSingleAnnotationOfType(AnnotatedClass.class, Component.class);

        // THEN
        assertThat(component.value(), is("component"));
    }

    private <T extends Annotation> List<T> getAnnotationEqualToClass(Set<Annotation> result, Class<T> annotationTypeToFind) {
        return result.stream()
                .filter(annotation -> annotation.annotationType().equals(annotationTypeToFind))
                .map(annotation -> (T) annotation)
                .collect(Collectors.toList());
    }

    static class TestClassWithAnnotation {
        @PostConstruct
        public void annotatedMethod() {

        }
    }

    @Component("component")
    @Service("service")
    @RepeatableAnnotation("a")
    @RepeatableAnnotation("b")
    static class AnnotatedClass {
    }

    @Repeatable(RepeatableAnnotations.class)
    @Retention(RetentionPolicy.RUNTIME)
    @interface RepeatableAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface RepeatableAnnotations {
        RepeatableAnnotation[] value();
    }
}
