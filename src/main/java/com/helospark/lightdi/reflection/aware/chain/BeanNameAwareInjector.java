package com.helospark.lightdi.reflection.aware.chain;

import com.helospark.lightdi.aware.BeanNameAware;
import com.helospark.lightdi.reflection.aware.AbstractTypeAwareDependencyInjectorChainItem;
import com.helospark.lightdi.reflection.aware.InjectAwareRequest;

public class BeanNameAwareInjector extends AbstractTypeAwareDependencyInjectorChainItem<BeanNameAware> {

    public BeanNameAwareInjector() {
        super(BeanNameAware.class);
    }

    @Override
    public void injectByAwareInterface(BeanNameAware object, InjectAwareRequest request) {
        object.setBeanName(request.getDependencyToCreate().getQualifier());
    }

}
