package com.helospark.lightdi.it.missingdependency;

import com.helospark.lightdi.annotation.Component;

@Component
public class DependencyC {
    private DependencyD dependency;

    public DependencyC(DependencyD dependency) {
        this.dependency = dependency;
    }

}
