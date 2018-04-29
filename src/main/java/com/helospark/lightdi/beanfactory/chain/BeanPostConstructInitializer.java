package com.helospark.lightdi.beanfactory.chain;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.aware.InjectAwareRequest;
import com.helospark.lightdi.reflection.aware.InterfaceAwareInjector;

public class BeanPostConstructInitializer {
    private PostConstructInvoker postConstructInvoker;
    private InterfaceAwareInjector interfaceAwareInjector;

    public BeanPostConstructInitializer(PostConstructInvoker postConstructInvoker, InterfaceAwareInjector interfaceAwareInjector) {
        this.postConstructInvoker = postConstructInvoker;
        this.interfaceAwareInjector = interfaceAwareInjector;
    }

    public void postProcessCreatedBean(LightDiContext context, DependencyDescriptor dependencyToCreate, Object createdBean) {
        injectByAwareInterfaces(context, dependencyToCreate, createdBean);
        postConstructInvoker.invokePostConstructMethods(dependencyToCreate, createdBean);
    }

    private void injectByAwareInterfaces(LightDiContext lightDiContext, DependencyDescriptor dependencyToCreate, Object createdBean) {
        InjectAwareRequest request = InjectAwareRequest.builder()
                .withContext(lightDiContext)
                .withCreatedBean(createdBean)
                .withDependencyToCreate(dependencyToCreate)
                .build();
        interfaceAwareInjector.injectByAwareInterfaces(request);
    }

}
