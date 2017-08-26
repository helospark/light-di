package com.helospark.lightdi;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;

public class BeanFactory {
    private ConstructorInvoker constructorInvoker;
    private SetterInvoker setterInvoker;
    private FieldSetInvoker fieldSetInvoker;
    private PostConstructInvoker postConstructInvoker;

    public BeanFactory(ConstructorInvoker constructorInvoker, SetterInvoker setterInvoker,
            FieldSetInvoker fieldSetInvoker, PostConstructInvoker postConstructInvoker) {
        this.constructorInvoker = constructorInvoker;
        this.setterInvoker = setterInvoker;
        this.fieldSetInvoker = fieldSetInvoker;
        this.postConstructInvoker = postConstructInvoker;
    }

    public Object createBean(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate) {
        try {
            Object result = constructorInvoker.invokeConstructor(lightDiContext, dependencyToCreate);
            setterInvoker.invokeSetters(lightDiContext, dependencyToCreate, result);
            fieldSetInvoker.setFieldValues(lightDiContext, dependencyToCreate, result);
            postConstructInvoker.invokePostConstructMethods(dependencyToCreate, result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
