package com.helospark.lightdi.definitioncollector;

import java.util.Arrays;
import java.util.List;

public class BeanDefinitionProcessorChainFactory {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionPreprocessorChain;

    public BeanDefinitionProcessorChainFactory() {
        StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem = new StereotypeBeanDefinitionCollectorChainItem();
        ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem = new ConfigurationClassBeanDefinitionCollectorChainItem();
        this.beanDefinitionPreprocessorChain = Arrays.asList(stereotypeBeanDefinitionCollectorChainItem,
                configurationClassBeanDefinitionCollectorChainItem);
    }

    public BeanDefinitionCollector createBeanDefinitionProcessorChain() {
        return new BeanDefinitionCollector(beanDefinitionPreprocessorChain);
    }
}
