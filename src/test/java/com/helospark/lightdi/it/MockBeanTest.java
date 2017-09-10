package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.it.testcontext1.FieldDependency;
import com.helospark.lightdi.it.testcontext1.TestDependency;
import com.helospark.lightdi.test.annotation.LightDiTest;
import com.helospark.lightdi.test.annotation.MockBean;
import com.helospark.lightdi.test.junit4.LightDiJUnitTestRunner;

@RunWith(LightDiJUnitTestRunner.class)
@LightDiTest(rootPackage = "com.helospark.lightdi.it.testcontext1")
public class MockBeanTest {
    @MockBean
    private TestDependency mockTestDependency;
    @Autowired
    private FieldDependency fieldDependency;

    @Test
    public void testAutowiredShouldProcessOnContextInit() {
        // GIVEN

        // WHEN
        TestDependency expectedToBeMock = fieldDependency.getRootDependency();

        // THEN
        assertNotNull(mockTestDependency);
        assertThat(expectedToBeMock, is(mockTestDependency));
        assertTrue(Mockito.mockingDetails(expectedToBeMock).isMock());
    }

}
