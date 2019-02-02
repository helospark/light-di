package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.missingdependency.MissingDependencyContextConfiguration;

public class MissingDependencyShouldPrintFailedBeanIT {

    @Test
    public void testFailedContextLoadShouldPrintFailedToCreateDependencyAndMissingDependency() {
        // GIVEN

        // WHEN
        Exception foundException = null;
        try {
            LightDiContext lightDi = LightDi.initContextByClass(MissingDependencyContextConfiguration.class);
            lightDi.close();
        } catch (Exception e) {
            foundException = e;
        }

        // THEN
        assertThat(foundException, is(not(nullValue())));
        assertThat(foundException.getCause().getMessage(), containsString("DependencyC"));
        assertThat(foundException.getCause().getCause().getMessage(), containsString("DependencyD"));
    }

}
