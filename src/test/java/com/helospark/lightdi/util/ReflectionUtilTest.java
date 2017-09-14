package com.helospark.lightdi.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

public class ReflectionUtilTest {

    @Test
    public void testExtractGenericTypeShouldReturnExpected() throws NoSuchMethodException, SecurityException {
        // GIVEN
        Method method = this.getClass().getMethod("reflectTest", List.class);

        // WHEN
        Optional<Class<?>> foundGenericParameter = ReflectionUtil.extractFirstGenericType(method.getParameters()[0]);

        // THEN
        assertTrue(foundGenericParameter.isPresent());
        assertTrue(foundGenericParameter.get().equals(String.class));
    }

    @Test
    public void testExtractGenericTypeShouldReturnExpected2() throws NoSuchMethodException, SecurityException {
        // GIVEN
        Method method = this.getClass().getMethod("reflectTest2", List.class);

        // WHEN
        Optional<Class<?>> foundGenericParameter = ReflectionUtil.extractFirstGenericType(method.getParameters()[0]);

        // THEN
        assertTrue(foundGenericParameter.isPresent());
        assertTrue(foundGenericParameter.get().equals(List.class));
    }

    @Test
    public void testExtractGenericTypeShouldReturnExpected3() throws NoSuchMethodException, SecurityException {
        // GIVEN
        Method method = this.getClass().getMethod("reflectTest3", String.class);

        // WHEN
        Optional<Class<?>> foundGenericParameter = ReflectionUtil.extractFirstGenericType(method.getParameters()[0]);

        // THEN
        assertFalse(foundGenericParameter.isPresent());
    }

    public void reflectTest(List<String> param) {

    }

    public void reflectTest2(List<List<String>> param) {

    }

    public void reflectTest3(String asd) {

    }
}
