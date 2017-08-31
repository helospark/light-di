package com.helospark.lightdi.beanfactory.chain.support;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;

public class AutowirePostProcessSupport {
    private SetterInvoker setterInvoker;
    private FieldSetInvoker fieldSetInvoker;

    public AutowirePostProcessSupport(SetterInvoker setterInvoker, FieldSetInvoker fieldSetInvoker) {
        this.setterInvoker = setterInvoker;
        this.fieldSetInvoker = fieldSetInvoker;
    }

    public void injectAutowired(LightDiContext lightDiContext, StereotypeDependencyDescriptor stereotypeDependency, Object result) {
        setterInvoker.invokeSetters(lightDiContext, stereotypeDependency, result);
        fieldSetInvoker.setFieldValues(lightDiContext, stereotypeDependency, result);
    }
}
