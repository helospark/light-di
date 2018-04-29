package com.helospark.lightdi.reflection.aware;

import com.helospark.lightdi.reflection.aware.InjectAwareRequest;

public interface AwareDependencyInjectorChainItem {

    public void injectByAwareInterface(InjectAwareRequest request);

    public boolean doesSupport(InjectAwareRequest request);

}
