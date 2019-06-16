package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.conditionaltest.NotABean;
import com.helospark.lightdi.it.customconditional.CustomConditionalTestConfiguration;
import com.helospark.lightdi.it.customconditional.beans.CustomValueConditionalBean;
import com.helospark.lightdi.it.customconditional.beans.MatchingNameBean;
import com.helospark.lightdi.it.customconditional.beans.NotMatchingNameBean;
import com.helospark.lightdi.properties.PropertySourceHolder;

public class CustomConditionalIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        context = LightDi.initContextByPackage(CustomConditionalTestConfiguration.class.getPackage().getName());
    }

    @Test
    public void testConditionalOnBeanWithMatchingNameShouldExist() {
        // GIVEN

        // WHEN
        MatchingNameBean matchingBean = context.getBean(MatchingNameBean.class);

        // THEN
        assertNotNull(matchingBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnMissingBeanShouldNotCreateBean() {
        // GIVEN

        // WHEN
        context.getBean(NotMatchingNameBean.class);

        // THEN throws
    }

    @Test
    public void testConditionalOnConfigurationBeanWithMatchingNameShouldExist() {
        // GIVEN

        // WHEN
        NotABean matchingBean = (NotABean) context.getBean("notabeanwithmatchingname");

        // THEN
        assertNotNull(matchingBean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnConfigurationBeanMissingBeanShouldNotCreateBean() {
        // GIVEN

        // WHEN
        context.getBean("notabeanwithnamenotmatching");

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConditionalOnCustomValueShouldBeMissingWithoutPropertyFile() {
        // GIVEN

        // WHEN
        context.getBean(CustomValueConditionalBean.class);

        // THEN throws
    }

    @Test
    public void testConditionalOnCustomValueShouldBeBeCreatedWhenPropertyIsDefined() {
        // GIVEN
        LightDiContext customContext = new LightDiContext();
        customContext.getEnvironment().addPropertySource(new PropertySourceHolder(-1, Collections.singletonMap("customValue", "create")));
        customContext.loadDependencyFromPackage(CustomConditionalTestConfiguration.class.getPackage().getName());

        // WHEN
        CustomValueConditionalBean customValue = customContext.getBean(CustomValueConditionalBean.class);

        // THEN 
        assertNotNull(customValue);
    }
}
