package com.helospark.lightdi.beanfactory.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;

public class StereotypeAnnotatedBeanFactoryChainItem implements BeanFactoryChainItem {
    private ConstructorInvoker constructorInvoker;
    private SetterInvoker setterInvoker;
    private FieldSetInvoker fieldSetInvoker;
    private PostConstructInvoker postConstructInvoker;

    public StereotypeAnnotatedBeanFactoryChainItem(ConstructorInvoker constructorInvoker, SetterInvoker setterInvoker,
            FieldSetInvoker fieldSetInvoker, PostConstructInvoker postConstructInvoker) {
        this.constructorInvoker = constructorInvoker;
        this.setterInvoker = setterInvoker;
        this.fieldSetInvoker = fieldSetInvoker;
        this.postConstructInvoker = postConstructInvoker;
    }

    @Override
    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) throws Exception {
        StereotypeDependencyDescriptor stereotypeDependency = (StereotypeDependencyDescriptor) dependencyToCreate;
        Object result = constructorInvoker.invokeConstructor(lightDiContext, stereotypeDependency);
        setterInvoker.invokeSetters(lightDiContext, stereotypeDependency, result);
        fieldSetInvoker.setFieldValues(lightDiContext, stereotypeDependency, result);
        postConstructInvoker.invokePostConstructMethods(stereotypeDependency, result);
        return result;
    }

    @Override
    public boolean isSupported(DependencyDescriptor dependencyDescriptor) {
        return dependencyDescriptor instanceof StereotypeDependencyDescriptor;
    }
}
