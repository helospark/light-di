package com.helospark.lightdi.reflection.aware.chain;

import com.helospark.lightdi.aware.ContextAware;
import com.helospark.lightdi.reflection.aware.AbstractTypeAwareDependencyInjectorChainItem;
import com.helospark.lightdi.reflection.aware.InjectAwareRequest;

public class ContextAwareInjector extends AbstractTypeAwareDependencyInjectorChainItem<ContextAware> {

    public ContextAwareInjector() {
        super(ContextAware.class);
    }

    @Override
    public void injectByAwareInterface(ContextAware object, InjectAwareRequest request) {
        object.setContext(request.getContext());
    }

}
