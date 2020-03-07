package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.autowiretoparent.Child;
import com.helospark.lightdi.it.autowiretoparent.Test;
import com.helospark.lightdi.it.autowiretoparent.TestContext;

public class ParentInjectionTest {

    @org.junit.Test
    public void testInjectToParentWithSetter() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(TestContext.class);

        // WHEN
        Child child = context.getBean(Child.class);
        Test test = child.getTest();

        // THEN
        assertNotNull(test);
    }

}
