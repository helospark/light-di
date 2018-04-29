package com.helospark.lightdi.reflection.aware.chain;

import com.helospark.lightdi.aware.EnvironmentAware;
import com.helospark.lightdi.reflection.aware.AbstractTypeAwareDependencyInjectorChainItem;
import com.helospark.lightdi.reflection.aware.InjectAwareRequest;

public class EnvironmentAwareInjector extends AbstractTypeAwareDependencyInjectorChainItem<EnvironmentAware> {

    public EnvironmentAwareInjector() {
        super(EnvironmentAware.class);
    }

    @Override
    public void injectByAwareInterface(EnvironmentAware object, InjectAwareRequest request) {
        object.setEnvironment(request.getContext().getEnvironment());
    }

}
