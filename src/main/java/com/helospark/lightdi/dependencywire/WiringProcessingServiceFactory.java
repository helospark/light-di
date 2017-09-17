package com.helospark.lightdi.dependencywire;

import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.dependencywire.chain.BeanDependencyWireChainItem;
import com.helospark.lightdi.dependencywire.chain.CommonDependencyWireChain;
import com.helospark.lightdi.dependencywire.chain.ComponentDependencyWireChainItem;
import com.helospark.lightdi.dependencywire.chain.DependencyWireChain;
import com.helospark.lightdi.dependencywire.chain.support.ConstructorWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.DependencyDescriptorBuilder;
import com.helospark.lightdi.dependencywire.chain.support.FieldWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.MethodDependencyCollector;
import com.helospark.lightdi.dependencywire.chain.support.SetterWireSupport;
import com.helospark.lightdi.dependencywire.chain.support.chain.CollectionInjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.DependencyInjectDescriptorBuilderChainItem;
import com.helospark.lightdi.dependencywire.chain.support.chain.ValueInjectDescriptorBuilderChainItem;

public class WiringProcessingServiceFactory {
    private ConstructorWireSupport constructorWireSupport;
    private SetterWireSupport setterWireSupport;
    private FieldWireSupport fieldWireSupport;
    private FindInDependencySupport findInDependencySupport;

    private ComponentDependencyWireChainItem componentDependencyWireChainItem;
    private CommonDependencyWireChain commonDependencyWireChain;
    private BeanDependencyWireChainItem beanDependencyWireChainItem;

    private List<DependencyWireChain> wireChain;

    public WiringProcessingServiceFactory() {
        findInDependencySupport = new FindInDependencySupport();
        PropertyDescriptorFactory propertyDescriptorFactory = new PropertyDescriptorFactory();

        CollectionInjectDescriptorBuilderChainItem collectionInjectDescriptorBuilderChainItem = new CollectionInjectDescriptorBuilderChainItem(
                findInDependencySupport);
        DependencyInjectDescriptorBuilderChainItem dependencyInjectDescriptorBuilderChainItem = new DependencyInjectDescriptorBuilderChainItem(
                findInDependencySupport);
        ValueInjectDescriptorBuilderChainItem valueInjectDescriptorBuilderChainItem = new ValueInjectDescriptorBuilderChainItem(
                propertyDescriptorFactory);

        DependencyDescriptorBuilder parameterDependencyDescriptorBuilder = new DependencyDescriptorBuilder(
                Arrays.asList(collectionInjectDescriptorBuilderChainItem,
                        valueInjectDescriptorBuilderChainItem, dependencyInjectDescriptorBuilderChainItem));

        constructorWireSupport = new ConstructorWireSupport(parameterDependencyDescriptorBuilder);

        MethodDependencyCollector methodDependencyCollector = new MethodDependencyCollector(
                parameterDependencyDescriptorBuilder);

        setterWireSupport = new SetterWireSupport(propertyDescriptorFactory, methodDependencyCollector);
        fieldWireSupport = new FieldWireSupport(parameterDependencyDescriptorBuilder);

        beanDependencyWireChainItem = new BeanDependencyWireChainItem(
                methodDependencyCollector);
        componentDependencyWireChainItem = new ComponentDependencyWireChainItem(
                constructorWireSupport, setterWireSupport, fieldWireSupport);
        commonDependencyWireChain = new CommonDependencyWireChain();

        wireChain = Arrays.asList(beanDependencyWireChainItem, componentDependencyWireChainItem,
                commonDependencyWireChain);
    }

    public WiringProcessingService createFieldWireSupport() {
        return new WiringProcessingService(wireChain);
    }

    public List<DependencyWireChain> getWireChain() {
        return wireChain;
    }

    public ComponentDependencyWireChainItem getComponentDependencyWireChainItem() {
        return componentDependencyWireChainItem;
    }

}
