package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Service;

@Service
public class MultiConstructorServer {
    private TestDependency testDependency;
    private String value;

    @Autowired
    public MultiConstructorServer(TestDependency testDependency) {
        this(testDependency, "setFromConstructor");
    }

    public MultiConstructorServer(TestDependency testDependency, String value) {
        this.testDependency = testDependency;
        this.value = value;
    }

    public TestDependency getTestDependency() {
        return testDependency;
    }

    public String getValue() {
        return value;
    }

}
