package com.helospark.lightdi.properties;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

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

    private ValueResolver valueResolver;

    private Environment underTest;

    @Before
    public void setUp() {
        initMocks(this);
        valueResolver = new ValueResolver(propertyStringResolver);
        when(propertyStringResolver.resolve(eq("${INT}"), any(List.class))).thenReturn("42");
        when(propertyStringResolver.resolve(eq("${STRING}"), any(List.class))).thenReturn("asd");
        when(propertyStringResolver.resolve(eq("${INT_ARRAY}"), any(List.class))).thenReturn("1,2,3");
        when(propertyStringResolver.resolve(eq("${STRING_ARRAY}"), any(List.class))).thenReturn("a,b,c");
        when(context.getListOfBeans(PropertyConverter.class)).thenReturn(
                asList(new StringPropertyConverter(),
                        new IntegerPropertyConverter()));
        underTest = new Environment(context, valueResolver);
    }

    @Test
    public void testResolveWithString() {
        // GIVEN

        // WHEN
        String result = underTest.resolve("${STRING}", String.class);

        // THEN
        assertThat(result, is("asd"));
    }

    @Test
    public void testResolveWithInt() {
        // GIVEN

        // WHEN
        Integer result = underTest.resolve("${INT}", Integer.class);

        // THEN
        assertThat(result, is(42));
    }

    @Test
    public void testResolveWithIntArray() {
        // GIVEN

        // WHEN
        Integer[] result = underTest.resolve("${INT_ARRAY}", Integer[].class);

        // THEN
        assertThat(result, is(new Integer[] { 1, 2, 3 }));
    }

    @Test
    public void testResolveWithStringArray() {
        // GIVEN

        // WHEN
        String[] result = underTest.resolve("${STRING_ARRAY}", String[].class);

        // THEN
        assertThat(result, is(new String[] { "a", "b", "c" }));
    }
}
