package com.helospark.lightdi.it;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.it.testcontext1.TestDependency;
import com.helospark.lightdi.test.LightDiTest;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;

@RunWith(LightDiJUnitTestRunner.class)
@LightDiTest(rootPackage = "com.helospark.lightdi.it.testcontext1")
public class JUnitRunnerTest {
    @Autowired
    private TestDependency testDependency;

    @Test
    public void testAutowiredShouldProcessOnContextInit() {
        // GIVEN

        // WHEN

        // THEN
        assertNotNull(testDependency);
    }

}
