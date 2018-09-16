package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Value;

public class PropertySourceEnvironmentIT {

    @Test
    public void testEnvironmentVariables() {
        // GIVEN
        assumeThat(System.getenv("PATH"), is(not(nullValue())));

        // WHEN
        LightDiContext context = LightDi.initContextByClass(PropertySourceEnvironmentTestContext.class);

        // THEN
        PropertySourceEnvironmentTestContext bean = context.getBean(PropertySourceEnvironmentTestContext.class);
        assertThat(bean.getPath(), is(not(nullValue())));
    }

    @Configuration
    public static class PropertySourceEnvironmentTestContext {
        @Value("${PATH}")
        private String path;

        public String getPath() {
            return path;
        }

    }
}
