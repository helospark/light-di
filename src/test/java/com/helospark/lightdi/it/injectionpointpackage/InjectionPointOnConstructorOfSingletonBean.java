package com.helospark.lightdi.it.injectionpointpackage;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.specialinject.InjectionPoint;

@Configuration
public class InjectionPointOnConstructorOfSingletonBean {
    private InjectionPoint injectionPoint;

    public InjectionPointOnConstructorOfSingletonBean(InjectionPoint injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

    public InjectionPoint getInjectionPoint() {
        return injectionPoint;
    }

}
