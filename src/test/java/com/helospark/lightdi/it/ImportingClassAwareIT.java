package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.exception.BeanCreationException;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.it.importingclassaware.DummyServiceConfiguration;
import com.helospark.lightdi.it.importingclassaware.ImportingClassAwareConfiguration;

public class ImportingClassAwareIT {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSuccessfullyInjected() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(ImportingClassAwareConfiguration.class);

        // THEN
        DummyServiceConfiguration dummyService = context.getBean(DummyServiceConfiguration.class);
        assertThat(dummyService.getConfigurationValue(), is("testConfiguration"));
    }

    @Test
    public void testOnDirectImportShouldThrow() {
        // GIVEN
        expectedException.expect(ContextInitializationFailedException.class);
        expectedException.expectCause(isA(BeanCreationException.class));

        // WHEN
        LightDi.initContextByClass(DummyServiceConfiguration.class);

        // THEN throws because empty is injected
    }
}
