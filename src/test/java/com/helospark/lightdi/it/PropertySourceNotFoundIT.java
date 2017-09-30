package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.it.propertysourcenotfoundcontext.NonRequiredPropertySourceNotFoundConfiguration;
import com.helospark.lightdi.it.propertysourcenotfoundcontext.RequiredPropertySourceNotFoundConfiguration;

public class PropertySourceNotFoundIT {
    LightDi lightDi = new LightDi();

    @Test(expected = ContextInitializationFailedException.class)
    public void testContextLoadWithMissingRequiredPropertySource() {
        // GIVEN

        // WHEN
        lightDi.initContextByClass(RequiredPropertySourceNotFoundConfiguration.class);

        // THEN throws
    }

    @Test
    public void testCollectionInject() {
        // GIVEN

        // WHEN
        LightDiContext context = lightDi.initContextByClass(NonRequiredPropertySourceNotFoundConfiguration.class);

        // THEN
        assertNotNull(context);
    }

}
