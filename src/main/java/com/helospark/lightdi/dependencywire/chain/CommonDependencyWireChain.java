package com.helospark.lightdi.dependencywire.chain;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class CommonDependencyWireChain implements DependencyWireChain {

    @Override
    public void collectDependencies(List<DependencyDescriptor> dependencyDescriptors, DependencyDescriptor dependency) {
        Class<?> clazz = dependency.getClazz();

        List<Method> postConstructMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PostConstruct.class);
        List<Method> preDestroyMethods = AnnotationUtil.getMethodsWithAnnotation(clazz, PreDestroy.class);

        dependency.setPostConstructMethods(postConstructMethods);
        dependency.setPreDestroyMethods(preDestroyMethods);
    }

}
