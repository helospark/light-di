package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.exception.ContextInitializationFailedException;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.it.contextwithcircle.ClassA;

public class ContextWithCircleTest {

    @Test
    public void testContextWithCircleShouldThrowExeption() {
        // GIVEN
        Exception thrownException = null;
        LightDi lightDi = new LightDi();

        // WHEN
        try {
            lightDi.initContextUsingFullClasspathScan(ClassA.class.getPackage().getName());
        } catch (Exception e) {
            thrownException = e;
        }
        // THEN
        assertNotNull(thrownException);
        assertTrue(thrownException instanceof ContextInitializationFailedException);
        assertTrue(thrownException.getCause() instanceof IllegalConfigurationException);
        assertThat(thrownException.getCause().getMessage(), is("Circle in bean definitions: { classB, classA }"));
    }

}
