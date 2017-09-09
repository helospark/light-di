package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.aware.ContextAware;

@Component
public class ContextAwareTest implements ContextAware {
    private LightDiContext context;

    public LightDiContext getContext() {
        return context;
    }

    @Override
    public void setContext(LightDiContext context) {
        this.context = context;
    }
}
