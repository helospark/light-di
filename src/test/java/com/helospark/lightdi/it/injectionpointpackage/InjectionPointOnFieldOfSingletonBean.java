package com.helospark.lightdi.it.injectionpointpackage;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.specialinject.InjectionPoint;

@Configuration
public class InjectionPointOnFieldOfSingletonBean {
    @Autowired
    private InjectionPoint injectionPoint;

    public InjectionPoint getInjectionPoint() {
        return injectionPoint;
    }

}
