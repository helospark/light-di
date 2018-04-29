package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.awaretest.BeanNameAwareContext;
import com.helospark.lightdi.it.awaretest.ContextAwareContext;
import com.helospark.lightdi.it.awaretest.EnvironmentAwareContext;

public class AwareInterfaceIT {

    @Test
    public void testBeanNameAware() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(BeanNameAwareContext.class);

        // THEN
        assertThat(context.getBean(BeanNameAwareContext.class).getBeanName(), is("customBeanName"));
    }

    @Test
    public void testContextAware() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(ContextAwareContext.class);

        // THEN
        assertThat(context.getBean(ContextAwareContext.class).getContext(), is(context));
    }

    @Test
    public void testEnvironmentAware() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(EnvironmentAwareContext.class);

        // THEN
        assertThat(context.getBean(EnvironmentAwareContext.class).getEnvironment(), is(context.getEnvironment()));
    }

}
