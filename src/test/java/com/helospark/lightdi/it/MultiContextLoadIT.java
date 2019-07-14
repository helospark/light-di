package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.it.multicontext.MainConfiguration1;
import com.helospark.lightdi.it.multicontext.MainConfiguration2;

public class MultiContextLoadIT {

    @Test
    public void testBeansCanBeAddedMultipleTimesWithMultiThreadedLoad() {
        // GIVEN
        LightDiContext context = new LightDiContext(LightDiContextConfiguration.builder().withThreadNumber(2).build());
        context.loadDependenciesFromClass(MainConfiguration1.class);
        context.loadDependenciesFromClass(MainConfiguration2.class);

        // WHEN
        MainConfiguration1 component1 = context.getBean(MainConfiguration1.class);
        MainConfiguration2 component2 = context.getBean(MainConfiguration2.class);

        // THEN
        assertNotNull(component1);
        assertNotNull(component2);
        context.close();
    }

}
