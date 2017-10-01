package com.helospark.lightdi.properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class PropertyValueResolverTest {
    private PropertyValueResolver propertyValueResolver;
    private List<PropertySourceHolder> properties;
    @Mock
    private PropertySourceHolder propertySourceHolder1;
    @Mock
    private PropertySourceHolder propertySourceHolder2;

    @Before
    public void setUp() {
        initMocks(this);
        properties = Arrays.asList(propertySourceHolder1, propertySourceHolder2);

        when(propertySourceHolder1.resolveProperty("firstProperty")).thenReturn("resolved1");
        when(propertySourceHolder2.resolveProperty("secondProperty")).thenReturn("resolved2");

        propertyValueResolver = new PropertyValueResolver();
    }

    @Test
    public void testResolveFromList1() {
        // GIVEN

        // WHEN
        Optional<String> result = propertyValueResolver.resolveOptional("firstProperty", properties);

        // THEN
        assertThat(result, is(Optional.of("resolved1")));
    }

    @Test
    public void testResolveFromList2() {
        // GIVEN

        // WHEN
        Optional<String> result = propertyValueResolver.resolveOptional("secondProperty", properties);

        // THEN
        assertThat(result, is(Optional.of("resolved2")));
    }

    @Test
    public void testResolveWhenAbsentShouldReturnOptionalEmpty() {
        // GIVEN

        // WHEN
        Optional<String> result = propertyValueResolver.resolveOptional("asdsd", properties);

        // THEN
        assertThat(result, is(Optional.empty()));
    }
}
