package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;

@RunWith(LightDiJUnitTestRunner.class)
@LightDiTest(rootPackage = "com.helospark.lightdi.it.propertyoverride")
public class PropertyOverrideIT {
    @Value("${TEST_VALUE}")
    private String value;

    @Test
    public void testValueShouldBeTheOverridden() {
        // GIVEN

        // WHEN

        // THEN
        assertThat(value, is("overridden"));
    }
}
