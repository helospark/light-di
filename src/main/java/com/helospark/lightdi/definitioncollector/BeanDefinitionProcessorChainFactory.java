package com.helospark.lightdi.definitioncollector;

import java.util.Arrays;
import java.util.List;

public class BeanDefinitionProcessorChainFactory {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionPreprocessorChain;

    public BeanDefinitionProcessorChainFactory() {
        this.beanDefinitionPreprocessorChain = Arrays.asList(new StereotypeBeanDefinitionCollectorChainItem());
    }

    public BeanDefinitionCollector createBeanDefinitionProcessorChain() {
        return new BeanDefinitionCollector(beanDefinitionPreprocessorChain);
    }
}
