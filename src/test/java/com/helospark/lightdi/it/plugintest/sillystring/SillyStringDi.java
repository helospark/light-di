package com.helospark.lightdi.it.plugintest.sillystring;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.internal.InternalDi;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;

public class SillyStringDi implements InternalDi {
    private InternalDi delegate;

    public SillyStringDi(InternalDi delegate) {
        this.delegate = delegate;
    }

    @Override
    public void initialize(LightDiContextConfiguration lightDiContextConfiguration) {
        delegate.initialize(lightDiContextConfiguration);
    }

    @Override
    public void addDependency(Object dependency) {
        Object dependencyToAdd = dependency;
        if (dependency.getClass().equals(PropertyObjectResolverChainItem.class)) {
            dependencyToAdd = createFakeDependencyResolver();
        }
        delegate.addDependency(dependencyToAdd);
    }

    @Override
    public <T> T getDependency(Class<T> clazz) {
        return delegate.getDependency(clazz);
    }

    @Override
    public <T> List<T> getDependencyList(Class<T> classToFind) {
        return delegate.getDependencyList(classToFind);
    }

    @Override
    public <T> List<T> getDependencyList(T... objects) {
        return delegate.getDependencyList(objects);
    }

    private DependencyObjectResolverChainItem createFakeDependencyResolver() {
        return new DependencyObjectResolverChainItem() {

            @Override
            public Object resolve(LightDiContext context, InjectionDescriptor injectionDescriptor) {
                return "Silly string :)";
            }

            @Override
            public boolean canHandle(InjectionDescriptor injectionDescriptor) {
                return true;
            }
        };
    }

}
