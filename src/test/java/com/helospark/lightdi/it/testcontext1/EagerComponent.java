package com.helospark.lightdi.it.testcontext1;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Eager;

@Component
@Eager
public class EagerComponent {
    public static boolean IS_INITIALIZED = false;

    @PostConstruct
    public void init() {
        IS_INITIALIZED = true;
    }

    @PreDestroy
    public void destroy() {
        IS_INITIALIZED = false;
    }
}
