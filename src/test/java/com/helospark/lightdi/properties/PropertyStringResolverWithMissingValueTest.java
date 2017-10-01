package com.helospark.lightdi.properties;

import static java.util.Optional.empty;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PropertyStringResolverWithMissingValueTest {
    private final List<PropertySourceHolder> propertySourceHolder = new ArrayList<>();
    @Mock
    private PropertyValueResolver propertyValueResolver;

    private PropertyStringResolver underTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PropertyStringResolver(propertyValueResolver);
        when(propertyValueResolver.resolveOptional("EMPTY", propertySourceHolder)).thenReturn(empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingValueShouldThrow() {
        // GIVEN

        // WHEN
        underTest.resolve("${EMPTY}", propertySourceHolder);

        // THEN throws
    }

}
