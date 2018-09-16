package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.it.testcontext1.ConstructorDependency;

public class TestParallelContextIT {

    @Test
    public void testRunningContextInParallelShouldStartWithoutIssue() {
        // GIVEN
        LightDiContextConfiguration config = LightDiContextConfiguration.builder()
                .withCheckForIntegrity(true)
                .withThreadNumber(2)
                .build();

        // WHEN
        LightDiContext context = LightDi.initContextByPackage(ConstructorDependency.class.getPackage().getName(),
                config);

        // THEN
        assertThat(context.getBean(ConstructorDependency.class), is(not(nullValue())));
    }

}
