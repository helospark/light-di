package com.helospark.lightdi.reflection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.setter.MethodDescriptor;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.specialinject.InjectionPoint;

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
                    .map(dependency -> resolveDependency(lightDiContext, dependency, dependencyToCreate, methodDescriptor))
                    .collect(Collectors.toList())
                    .toArray();
            return methodDescriptor.getMethod().invoke(result, arguments);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to call setter " + methodDescriptor, e);
        }
    }

    private Object resolveDependency(LightDiContext context, InjectionDescriptor descriptor, DependencyDescriptor dependencyToCreate,
            MethodDescriptor methodDescriptor) {
        InjectionPoint injectionPoint = InjectionPoint.builder()
                .withMethodDescriptor(Optional.of(methodDescriptor))
                .withInjectionDescriptor(descriptor)
                .withDependencyDescriptor(dependencyToCreate)
                .build();

        return dependencyObjectResolverHandler.resolve(context, descriptor, injectionPoint);
    }

}
