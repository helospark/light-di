package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnBeanShouldCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnBeanShouldNotCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnBeanToConditionalOnClass;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnClassShouldCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnClassShouldNotCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnMissingBeanShouldCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnMissingBeanShouldNotCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnMissingClassShouldCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnMissingClassShouldNotCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnPropertyShouldCreateBean;
import com.helospark.lightdi.it.conditionaltest.ConditionalOnPropertyShouldNotCreateBean;
import com.helospark.lightdi.it.conditionaltest.TestConfiguration;

public class ConditionalIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = lightDi.initContextByPackage(TestConfiguration.class.getPackage().getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnBeanBeanShouldNotExist() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnBeanShouldNotCreateBean.class);

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnClassShouldNotExist() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnClassShouldNotCreateBean.class);

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnPropertyShouldNotExist() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnPropertyShouldNotCreateBean.class);

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnConditionShouldNotExist() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnBeanToConditionalOnClass.class);

        // THEN throws
    }

    @Test
    public void testConditionalOnPropertyExistShouldCreateBean() {
        // GIVEN

        // WHEN
        ConditionalOnPropertyShouldCreateBean result = context.getBean(ConditionalOnPropertyShouldCreateBean.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testConditionalOnClassExistShouldCreateBean() {
        // GIVEN

        // WHEN
        ConditionalOnClassShouldCreateBean result = context.getBean(ConditionalOnClassShouldCreateBean.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testConditionalOnBeanExistShouldCreateBean() {
        // GIVEN

        // WHEN
        ConditionalOnBeanShouldCreateBean result = context.getBean(ConditionalOnBeanShouldCreateBean.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testConditionalOnMissingBeanShouldCreateBean() {
        // GIVEN

        // WHEN
        ConditionalOnMissingBeanShouldCreateBean result = context.getBean(ConditionalOnMissingBeanShouldCreateBean.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testConditionalOnMissingClassShouldCreateBean() {
        // GIVEN

        // WHEN
        ConditionalOnMissingClassShouldCreateBean result = context.getBean(ConditionalOnMissingClassShouldCreateBean.class);

        // THEN
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnMissingClassShouldNotCreateBean() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnMissingClassShouldNotCreateBean.class);

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnMissingBeanShouldNotCreateBean() {
        // GIVEN

        // WHEN
        context.getBean(ConditionalOnMissingBeanShouldNotCreateBean.class);

        // THEN throws
    }
}
