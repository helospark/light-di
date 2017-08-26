package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

@Component
public class SetterDependency {
    private TestDependency rootDependency;

    public TestDependency getRootDependency() {
        return rootDependency;
    }

    @Autowired
    public void setRootDependency(TestDependency rootDependency) {
        this.rootDependency = rootDependency;
    }

}
