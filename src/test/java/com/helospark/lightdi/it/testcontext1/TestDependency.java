package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;

@Component
public class TestDependency {

    public TestDependency() {

    }

    @Component
    public static class InnerClass {

    }
}
