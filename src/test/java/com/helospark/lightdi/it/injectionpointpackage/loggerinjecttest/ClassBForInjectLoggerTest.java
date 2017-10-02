package com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;

@Component
public class ClassBForInjectLoggerTest {
    @Autowired
    private Slf4jStubLogger logger;

    public Slf4jStubLogger getLogger() {
        return logger;
    }

}
