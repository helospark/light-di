package com.helospark.lightdi.properties;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(Parameterized.class)
public class PropertyStringResolverTest {
    @Mock
    private PropertyValueResolver propertyValueResolver;

    private PropertyStringResolver underTest;

    private String expectedInput;
    private String expectedOutput;

    public PropertyStringResolverTest(String expectedInput, String expectedOutput) {
        this.expectedInput = expectedInput;
        this.expectedOutput = expectedOutput;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PropertyStringResolver(propertyValueResolver);
        when(propertyValueResolver.resolveOptional("TEST_VALUE")).thenReturn(of("testValueResolved"));
        when(propertyValueResolver.resolveOptional("NESTED_VALUE")).thenReturn(of("nested=${TEST_VALUE}"));
        when(propertyValueResolver.resolveOptional("DOUBLE_NESTED")).thenReturn(of("${NESTED_VALUE}=${TEST_VALUE}"));
        when(propertyValueResolver.resolveOptional("EMPTY")).thenReturn(empty());
    }

    @Test
    public void test() {
        // GIVEN

        // WHEN
        String result = underTest.resolve(expectedInput);

        // THEN
        assertThat(result, is(expectedOutput));
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[] { "${TEST_VALUE}", "testValueResolved" },
                new Object[] { "test=${TEST_VALUE}", "test=testValueResolved" },
                new Object[] { "${NESTED_VALUE}", "nested=testValueResolved" },
                new Object[] { "${DOUBLE_NESTED}", "nested=testValueResolved=testValueResolved" },
                new Object[] { "${EMPTY}", "" },
                new Object[] { "isItEmpty=${EMPTY}", "isItEmpty=" });
    }
}
