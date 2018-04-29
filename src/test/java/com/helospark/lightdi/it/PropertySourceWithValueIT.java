package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.customconverter.ComponentWithProperty;
import com.helospark.lightdi.it.customconverter.CustomConverterContextConfiguration;
import com.helospark.lightdi.it.propertysourcewithvalue.PropertySourceWithEnvironmentVariableConfiguration;

public class PropertySourceWithValueIT {

    @Test
    public void testShouldParseStagingConfiguration() {
        // GIVEN
        System.setProperty("lightdi.environment", "staging");
        LightDiContext context = LightDi.initContextByClass(PropertySourceWithEnvironmentVariableConfiguration.class);

        // WHEN
        PropertySourceWithEnvironmentVariableConfiguration result = context.getBean(PropertySourceWithEnvironmentVariableConfiguration.class);

        // THEN
        assertThat(result.getTestValue(), is("staging"));

        // CLEANUP
        System.clearProperty("lightdi.environment");
    }

    @Test
    public void testShouldParseDefaultConfiguration() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(PropertySourceWithEnvironmentVariableConfiguration.class);

        // WHEN
        PropertySourceWithEnvironmentVariableConfiguration result = context.getBean(PropertySourceWithEnvironmentVariableConfiguration.class);

        // THEN
        assertThat(result.getTestValue(), is("default"));
    }

    @Test
    public void testShouldConvertWithCustomConverter() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(CustomConverterContextConfiguration.class);

        // WHEN
        ComponentWithProperty result = context.getBean(ComponentWithProperty.class);

        // THEN
        assertThat(result.getBigInteger(), is(BigInteger.ONE));
    }
}
