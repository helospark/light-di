package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
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
}
