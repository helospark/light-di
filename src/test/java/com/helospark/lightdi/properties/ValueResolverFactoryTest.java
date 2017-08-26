package com.helospark.lightdi.properties;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.properties.converter.StringPropertyConverter;

public class ValueResolverFactoryTest {
    private ValueResolverFactory underTest;
    @Mock
    private LightDiContext context;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new ValueResolverFactory();
        when(context.getListOfBeans(PropertyConverter.class)).thenReturn(
                asList(new StringPropertyConverter()));

    }

    @Test
    public void testFactoryShouldLoadFileAndReturnUsableValueResolver() {
        // GIVEN
        DependencyDescriptor descriptor = DependencyDescriptor.builder()
                .withClazz(PropertySourceAnnotatedClass.class)
                .build();

        // WHEN
        ValueResolver valueResolver = underTest.createValueResolver(Arrays.asList(descriptor));

        // THEN
        String resolvedTestValue = valueResolver.resolve("${TEST_VALUE}", String.class, context);
        assertThat(resolvedTestValue, is("asd"));
    }

    @PropertySource("classpath:test_properties.properties")
    static class PropertySourceAnnotatedClass {

    }
}
