package com.helospark.lightdi.it.awaretest;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.aware.ContextAware;

@Configuration
public class ContextAwareContext implements ContextAware {
    private LightDiContext context;

    public LightDiContext getContext() {
        return context;
    }

    @Override
    public void setContext(LightDiContext context) {
        this.context = context;
    }

}
