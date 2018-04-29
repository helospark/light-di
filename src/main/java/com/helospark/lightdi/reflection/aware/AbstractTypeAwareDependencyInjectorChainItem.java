package com.helospark.lightdi.reflection.aware;

public abstract class AbstractTypeAwareDependencyInjectorChainItem<T> implements AwareDependencyInjectorChainItem {
    private Class<T> type;

    public AbstractTypeAwareDependencyInjectorChainItem(Class<T> type) {
        this.type = type;
    }

    @Override
    public void injectByAwareInterface(InjectAwareRequest request) {
        if (doesSupport(request)) {
            injectByAwareInterface(type.cast(request.getCreatedBean()), request);
        }
    }

    public abstract void injectByAwareInterface(T object, InjectAwareRequest request);

    @Override
    public boolean doesSupport(InjectAwareRequest request) {
        return type.isAssignableFrom(request.getCreatedBean().getClass());
    }

}
