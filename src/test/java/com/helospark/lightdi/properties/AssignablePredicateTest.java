package com.helospark.lightdi.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AssignablePredicateTest {
    private AssignablePredicate assignablePredicate;

    @Before
    public void setUp() {
        assignablePredicate = new AssignablePredicate();
    }

    @Test
    public void testPrimitiveConversion() {
        // GIVEN

        // WHEN
        boolean result = assignablePredicate.canBeAssigned(Integer.class, int.class);

        // THEN
        assertTrue(result);
    }

    @Test
    public void testClassConversion() {
        // GIVEN

        // WHEN
        boolean result = assignablePredicate.canBeAssigned(RuntimeException.class, Exception.class);

        // THEN
        assertThat(result, is(true));
    }
}
