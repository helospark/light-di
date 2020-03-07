package com.helospark.lightdi.dependencywire.chain;

import java.lang.reflect.Method;
import java.util.List;
import java.util.SortedSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.helospark.lightdi.dependencywire.chain.support.FieldWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.SetterWireSupport;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.field.FieldDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class CommonDependencyWireChain implements DependencyWireChain {
    private SetterWireSupport setterWireSupport;
    private FieldWireSupport fieldWireSupport;

    public CommonDependencyWireChain(SetterWireSupport setterWireSupport, FieldWireSupport fieldWireSupport) {
        this.setterWireSupport = setterWireSupport;
        this.fieldWireSupport = fieldWireSupport;
    }

    @Override
    public void collectDependencies(SortedSet<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependencyDescriptor) {
        Class<?> clazz = dependencyDescriptor.getClazz();

        List<Method> postConstructMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PostConstruct.class);
        List<Method> preDestroyMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PreDestroy.class);
        List<MethodDescriptor> setterDependencies = setterWireSupport.getSetterDependencies(clazz, dependencyDescriptors, dependencyDescriptor);
        List<FieldDescriptor> fieldDescriptors = fieldWireSupport.getFieldDependencies(clazz, dependencyDescriptors, dependencyDescriptor);

        dependencyDescriptor.setPostConstructMethods(postConstructMethods);
        dependencyDescriptor.setPreDestroyMethods(preDestroyMethods);
        dependencyDescriptor.setSetterDescriptor(setterDependencies);
        dependencyDescriptor.setFieldDescriptor(fieldDescriptors);
    }

}
