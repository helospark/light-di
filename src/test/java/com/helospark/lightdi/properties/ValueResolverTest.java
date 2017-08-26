package com.helospark.lightdi.properties;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.properties.converter.IntegerPropertyConverter;
import com.helospark.lightdi.properties.converter.StringPropertyConverter;

public class ValueResolverTest {
    @Mock
    private PropertyStringResolver propertyStringResolver;
    @Mock
    private LightDiContext context;

    private ValueResolver underTest;

    @Before
    public void setUp() {
        initMocks(this);
        underTest = new ValueResolver(propertyStringResolver);
        when(propertyStringResolver.resolve("${INT}")).thenReturn("42");
        when(propertyStringResolver.resolve("${STRING}")).thenReturn("asd");
        when(propertyStringResolver.resolve("${INT_ARRAY}")).thenReturn("1,2,3");
        when(propertyStringResolver.resolve("${STRING_ARRAY}")).thenReturn("a,b,c");
        when(context.getListOfBeans(PropertyConverter.class)).thenReturn(
                asList(new StringPropertyConverter(),
                        new IntegerPropertyConverter()));
    }

    @Test
    public void testResolveWithString() {
        // GIVEN

        // WHEN
        String result = underTest.resolve("${STRING}", String.class, context);

        // THEN
        assertThat(result, is("asd"));
    }

    @Test
    public void testResolveWithInt() {
        // GIVEN

        // WHEN
        Integer result = underTest.resolve("${INT}", Integer.class, context);

        // THEN
        assertThat(result, is(42));
    }

    @Test
    public void testResolveWithIntArray() {
        // GIVEN

        // WHEN
        Integer[] result = underTest.resolve("${INT_ARRAY}", Integer[].class, context);

        // THEN
        assertThat(result, is(new Integer[] { 1, 2, 3 }));
    }

    @Test
    public void testResolveWithStringArray() {
        // GIVEN

        // WHEN
        String[] result = underTest.resolve("${STRING_ARRAY}", String[].class, context);

        // THEN
        assertThat(result, is(new String[] { "a", "b", "c" }));
    }
}
