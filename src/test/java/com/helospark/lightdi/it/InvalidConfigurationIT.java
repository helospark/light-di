package com.helospark.lightdi.it;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.it.invalidconfiguration.BothLazyAndEagerInitialized;
import com.helospark.lightdi.it.invalidconfiguration.ComponentWithMultipleAutowiredAnnotatedConstructor;
import com.helospark.lightdi.it.invalidconfiguration.ComponentWithMultipleNonAnnotatedConstructor;

public class InvalidConfigurationIT {
    private LightDi lightDi = new LightDi();

    @Test(expected = ContextInitializationFailedException.class)
    public void testContextInitShouldFailOnMultipleAutowiredAnnotatedConstructor() {
        // GIVEN

        // WHEN
        lightDi.initContextByClass(ComponentWithMultipleAutowiredAnnotatedConstructor.class);

        // THEN throws
    }

    @Test(expected = ContextInitializationFailedException.class)
    public void testContextInitShouldFailOnMultipleNonAnnotatedConstructor() {
        // GIVEN

        // WHEN
        lightDi.initContextByClass(ComponentWithMultipleNonAnnotatedConstructor.class);

        // THEN throws
    }

    @Test(expected = ContextInitializationFailedException.class)
    public void testContextInitShouldFailOnBothLazyAndEagerBean() {
        // GIVEN

        // WHEN
        lightDi.initContextByClass(BothLazyAndEagerInitialized.class);

        // THEN throws
    }
}
