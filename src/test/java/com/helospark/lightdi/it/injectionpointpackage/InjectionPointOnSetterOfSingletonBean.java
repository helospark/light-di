package com.helospark.lightdi.it.injectionpointpackage;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.specialinject.InjectionPoint;

@Configuration
public class InjectionPointOnSetterOfSingletonBean {
    private InjectionPoint injectionPoint;

    public InjectionPointOnSetterOfSingletonBean(InjectionPoint injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

    public InjectionPoint getInjectionPoint() {
        return injectionPoint;
    }

    @Autowired
    public void setInjectionPoint(InjectionPoint injectionPoint) {
        this.injectionPoint = injectionPoint;
    }

}
