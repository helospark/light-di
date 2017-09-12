package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.testcontext1.ComponentWithConstructorValue;

public class SystemPropertyInjectIT {

    @Test
    public void testSystemPropertyShouldInject() {
        // GIVEN
        System.setProperty("TEST_VALUE", "systemPropertyOverride");
        LightDiContext context = new LightDi().initContextByPackage(ComponentWithConstructorValue.class.getPackage().getName());

        // WHEN
        ComponentWithConstructorValue instance = context.getBean(ComponentWithConstructorValue.class);

        // THEN
        assertThat(instance.getValue(), is("systemPropertyOverride"));
    }
}
