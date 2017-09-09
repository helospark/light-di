package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.beanpostprocessortest.TestBean;
import com.helospark.lightdi.it.beanpostprocessortest.TestBeanPostProcessor;

public class BeanPostProcessorTest {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = lightDi.initContextByPackage(TestBeanPostProcessor.class.getPackage().getName());
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void testBeanPostProcessorShouldHaveSetValue() {
        // GIVEN

        // WHEN
        TestBean testBean = context.getBean(TestBean.class);

        // THEN
        assertThat(testBean.getValue(), is("setFromPostProcessor"));
    }
}
