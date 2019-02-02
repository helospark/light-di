package com.helospark.lightdi.it.missingdependency;

import com.helospark.lightdi.annotation.Component;

@Component
public class DependencyB {
    private DependencyC dependecy;

    public DependencyB(DependencyC dependecy) {
        this.dependecy = dependecy;
    }

}
