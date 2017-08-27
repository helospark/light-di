package com.helospark.lightdi.it.testcontext1;

public class OtherNonAnnotatedClass {
    private TestDependency dependency;

    public OtherNonAnnotatedClass(TestDependency dependency) {
        this.dependency = dependency;
    }

    public TestDependency getDependency() {
        return dependency;
    }

}
