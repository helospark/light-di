package com.helospark.lightdi;

import java.util.Arrays;

import com.helospark.lightdi.reflection.ConstructorInvoker;
import com.helospark.lightdi.reflection.FieldSetInvoker;
import com.helospark.lightdi.reflection.PostConstructInvoker;
import com.helospark.lightdi.reflection.SetterInvoker;
import com.helospark.lightdi.reflection.chain.DependencyObjectResolverHandler;
import com.helospark.lightdi.reflection.chain.DependentObjectResolverChainItem;
import com.helospark.lightdi.reflection.chain.PropertyObjectResolverChainItem;

public class BeanFactoryFactory {

    public BeanFactory createBeanFactory() {
        DependentObjectResolverChainItem dependentObjectResolverChainItem = new DependentObjectResolverChainItem();
        PropertyObjectResolverChainItem propertyObjectResolverChainItem = new PropertyObjectResolverChainItem();

        DependencyObjectResolverHandler dependencyObjectResolverHandler = new DependencyObjectResolverHandler(
                Arrays.asList(dependentObjectResolverChainItem, propertyObjectResolverChainItem));

        ConstructorInvoker constructorInvoker = new ConstructorInvoker(dependencyObjectResolverHandler);
        SetterInvoker setterInvoker = new SetterInvoker(dependencyObjectResolverHandler);
        FieldSetInvoker fieldSetInvoker = new FieldSetInvoker(dependencyObjectResolverHandler);

        PostConstructInvoker postConstructInvoker = new PostConstructInvoker();

        return new BeanFactory(constructorInvoker, setterInvoker, fieldSetInvoker, postConstructInvoker);
    }
}
