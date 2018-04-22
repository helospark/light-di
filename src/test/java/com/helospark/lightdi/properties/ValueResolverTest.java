package com.helospark.lightdi.properties;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.GenericClass;
import com.helospark.lightdi.properties.converter.IntegerPropertyConverter;
import com.helospark.lightdi.properties.converter.StringPropertyConverter;
import com.helospark.lightdi.util.CollectionFactory;

public class ValueResolverTest {
    @Mock
    private PropertyStringResolver propertyStringResolver;
    @Mock
    private LightDiContext context;

    private ValueResolver valueResolver;

    private AssignablePredicate assignablePredicate;

    private Environment underTest;

    @Before
    public void setUp() {
        initMocks(this);
        assignablePredicate = new AssignablePredicate();
        CollectionFactory collectionFactory = new CollectionFactory();
        valueResolver = new ValueResolver(propertyStringResolver, assignablePredicate, collectionFactory);
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
        String result = underTest.resolve("${STRING}", new GenericClass(String.class));

        // THEN
        assertThat(result, is("asd"));
    }

    @Test
    public void testResolveWithInt() {
        // GIVEN

        // WHEN
        Integer result = underTest.resolve("${INT}", new GenericClass(Integer.class));

        // THEN
        assertThat(result, is(42));
    }

    @Test
    public void testResolveWithIntArray() {
        // GIVEN

        // WHEN
        Integer[] result = underTest.resolve("${INT_ARRAY}", new GenericClass(Integer[].class));

        // THEN
        assertThat(result, is(new Integer[] { 1, 2, 3 }));
    }

    @Test
    public void testResolveWithStringArray() {
        // GIVEN

        // WHEN
        String[] result = underTest.resolve("${STRING_ARRAY}", new GenericClass(String[].class));

        // THEN
        assertThat(result, is(new String[] { "a", "b", "c" }));
    }

    @Test
    public void testResolveWithStringList() {
        // GIVEN

        // WHEN
        List<String> result = underTest.resolve("${STRING_ARRAY}", new GenericClass(List.class, Optional.of(String.class)));

        // THEN
        assertThat(result, is(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void testResolveWithIntegerSet() {
        // GIVEN

        // WHEN
        Set<Integer> result = underTest.resolve("${INT_ARRAY}", new GenericClass(Set.class, Optional.of(Integer.class)));

        // THEN
        assertThat(result, is(new HashSet<>(asList(1, 2, 3))));
    }
}
