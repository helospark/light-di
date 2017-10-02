package com.helospark.lightdi.it.injectionpointpackage;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Scope;
import com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.ClassAForInjectLoggerTest;
import com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.Slf4jStubLogger;
import com.helospark.lightdi.specialinject.InjectionPoint;

@Configuration
@ComponentScan(basePackageClasses = ClassAForInjectLoggerTest.class)
public class PrototypeInjectionPointTestConfiguration {

    @Bean
    @Scope("prototype")
    public Slf4jStubLogger logger(InjectionPoint injectionPoint) {
        String loggerName = injectionPoint.getFieldDescriptor().get().getField().getDeclaringClass().getName();
        return new Slf4jStubLogger(loggerName);
    }
}
