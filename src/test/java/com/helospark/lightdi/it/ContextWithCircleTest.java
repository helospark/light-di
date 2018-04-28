package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.it.contextwithcircle.ClassA;

public class ContextWithCircleTest {

    @Test
    public void testContextWithCircleShouldThrowExeptionWhenIntegrityCheckIsTurnedOn() {
        // GIVEN
        Exception thrownException = null;
        LightDi lightDi = new LightDi();
        LightDiContextConfiguration configuration = LightDiContextConfiguration.builder()
                .withCheckForIntegrity(true)
                .build();

        // WHEN
        try {
            LightDi.initContextByPackage(ClassA.class.getPackage().getName(), configuration);
        } catch (Exception e) {
            thrownException = e;
        }

        // THEN
        assertThatCircularBeanDefinitionExceptionThrown(thrownException);
    }

    @Test
    public void testContextWithCircleShouldNotThrowExceptionWhenIntegrityCheckIsTurnedOff() {
        // GIVEN
        Exception thrownException = null;
        LightDi lightDi = new LightDi();
        LightDiContextConfiguration configuration = LightDiContextConfiguration.builder()
                .withCheckForIntegrity(false)
                .build();

        // WHEN
        try {
            LightDi.initContextByPackage(ClassA.class.getPackage().getName(), configuration);
        } catch (Exception e) {
            thrownException = e;
        }
        // THEN
        assertNull(thrownException);
    }

    @Test
    public void testContextWithCircleShouldThrowExeptionWithDefaultConfiguration() {
        // GIVEN
        Exception thrownException = null;
        LightDi lightDi = new LightDi();

        // WHEN
        try {
            LightDi.initContextByPackage(ClassA.class.getPackage().getName());
        } catch (Exception e) {
            thrownException = e;
        }

        // THEN
        assertThatCircularBeanDefinitionExceptionThrown(thrownException);
    }

    private void assertThatCircularBeanDefinitionExceptionThrown(Exception thrownException) {
        assertNotNull(thrownException);
        assertTrue(thrownException instanceof ContextInitializationFailedException);
        assertTrue(thrownException.getCause() instanceof IllegalConfigurationException);
        assertThat(thrownException.getCause().getMessage(), is("Circle in bean definitions: { classB, classA }"));
    }

}
