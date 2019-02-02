package com.helospark.lightdi.it.missingdependency;

import com.helospark.lightdi.annotation.Component;

@Component
public class DependencyA {
    private DependencyB dependency;

    public DependencyA(DependencyB dependency) {
        this.dependency = dependency;
    }

}
