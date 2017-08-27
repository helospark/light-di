package com.helospark.lightdi.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;

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

    static class TestClassWithAnnotation {
        @PostConstruct
        public void annotatedMethod() {

        }
    }
}
