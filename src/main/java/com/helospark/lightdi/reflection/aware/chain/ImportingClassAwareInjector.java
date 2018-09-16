package com.helospark.lightdi.reflection.aware.chain;

import com.helospark.lightdi.aware.ImportingClassAware;
import com.helospark.lightdi.reflection.aware.AbstractTypeAwareDependencyInjectorChainItem;
import com.helospark.lightdi.reflection.aware.InjectAwareRequest;

public class ImportingClassAwareInjector extends AbstractTypeAwareDependencyInjectorChainItem<ImportingClassAware> {

    public ImportingClassAwareInjector() {
        super(ImportingClassAware.class);
    }

    @Override
    public void injectByAwareInterface(ImportingClassAware object, InjectAwareRequest request) {
        object.setImportingClass(request.getDependencyToCreate().getImportingClass());
    }

}
