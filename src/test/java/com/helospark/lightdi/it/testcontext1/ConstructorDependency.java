package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;

@Component
public class ConstructorDependency {
    private TestDependency rootDependency;

    public ConstructorDependency(TestDependency rootDependency) {
        this.rootDependency = rootDependency;
    }

    public void doStuff() {
        System.out.println("ok");
    }

    public TestDependency getRootDependency() {
        return rootDependency;
    }

}
