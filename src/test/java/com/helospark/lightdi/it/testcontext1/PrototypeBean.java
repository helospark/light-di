package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Scope;
import com.helospark.lightdi.constants.LightDiConstants;

@Component
@Scope(LightDiConstants.SCOPE_PROTOTYPE)
public class PrototypeBean {
    private TestDependency testDependency;

    public PrototypeBean(TestDependency testDependency) {
        this.testDependency = testDependency;
    }

    public TestDependency getTestDependency() {
        return testDependency;
    }

}
