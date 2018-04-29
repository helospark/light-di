package com.helospark.lightdi.reflection;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;

public class MethodInvoker {
    private DependencyObjectResolverHandler dependencyObjectResolverHandler;

    public MethodInvoker(DependencyObjectResolverHandler dependencyObjectResolverHandler) {
        this.dependencyObjectResolverHandler = dependencyObjectResolverHandler;
    }

    public Object invokeMethod(LightDiContext lightDiContext, MethodDescriptor methodDescriptor,
            Object result, DependencyDescriptor dependencyToCreate) {
        try {
            List<InjectionDescriptor> injectionDescriptor = methodDescriptor.getInjectionDescriptor();
            Object[] arguments = injectionDescriptor.stream()
                    .map(dependency -> resolveDependency(lightDiContext, dependency, dependencyToCreate))
                    .collect(Collectors.toList())
                    .toArray();
            Method method = methodDescriptor.getMethod();
            method.setAccessible(true);
            return method.invoke(result, arguments);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to call setter " + methodDescriptor, e);
        }
    }

    private Object resolveDependency(LightDiContext context, InjectionDescriptor descriptor, DependencyDescriptor dependencyToCreate) {
        return dependencyObjectResolverHandler.resolve(context, descriptor);
    }

}
