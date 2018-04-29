package com.helospark.lightdi.reflection.aware;

import java.util.List;

public class InterfaceAwareInjector {
    private List<AwareDependencyInjectorChainItem> chain;

    public InterfaceAwareInjector(List<AwareDependencyInjectorChainItem> chain) {
        this.chain = chain;
    }

    public void injectByAwareInterfaces(InjectAwareRequest request) {
        chain.stream()
                .filter(chainItem -> chainItem.doesSupport(request))
                .forEach(chainItem -> chainItem.injectByAwareInterface(request));
    }
}
