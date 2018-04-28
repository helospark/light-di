package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.othertestpackage.TestComponentInDifferentPackage;
import com.helospark.lightdi.it.testclassload.ClassInitConfigurationClass;
import com.helospark.lightdi.it.testclassload.OtherBeanInSamePackage;

public class ContextInitByClassIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = LightDi.initContextByClass(ClassInitConfigurationClass.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void testContextShouldLoadGivenAnnotatedClass() {
        // GIVEN

        // WHEN
        ClassInitConfigurationClass bean = context.getBean(ClassInitConfigurationClass.class);

        // THEN
        assertNotNull(bean);
    }

    @Test
    public void testContextShouldLoadComponentScannerClass() {
        // GIVEN

        // WHEN
        TestComponentInDifferentPackage bean = context.getBean(TestComponentInDifferentPackage.class);

        // THEN
        assertNotNull(bean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContextShouldNotLoadOtherClassesInSamePackage() {
        // GIVEN

        // WHEN
        context.getBean(OtherBeanInSamePackage.class);

        // THEN throws
    }
}
