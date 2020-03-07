package com.helospark.lightdi.beanfactory.chain.support;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;

public class AutowirePostProcessSupport {
    private SetterInvoker setterInvoker;
    private FieldSetInvoker fieldSetInvoker;

    public AutowirePostProcessSupport(SetterInvoker setterInvoker, FieldSetInvoker fieldSetInvoker) {
        this.setterInvoker = setterInvoker;
        this.fieldSetInvoker = fieldSetInvoker;
    }

    public void injectAutowired(LightDiContext lightDiContext, DependencyDescriptor dependencyDescriptor, Object result) {
        setterInvoker.invokeSetters(lightDiContext, dependencyDescriptor, result);
        fieldSetInvoker.setFieldValues(lightDiContext, dependencyDescriptor, result);
    }
}
