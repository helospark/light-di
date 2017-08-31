package com.helospark.lightdi.definitioncollector;

import java.util.Arrays;
import java.util.List;

public class BeanDefinitionProcessorChainFactory {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionPreprocessorChain;
    private StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem;
    private ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem;

    public BeanDefinitionProcessorChainFactory() {
        stereotypeBeanDefinitionCollectorChainItem = new StereotypeBeanDefinitionCollectorChainItem();
        configurationClassBeanDefinitionCollectorChainItem = new ConfigurationClassBeanDefinitionCollectorChainItem();
        this.beanDefinitionPreprocessorChain = Arrays.asList(stereotypeBeanDefinitionCollectorChainItem,
                configurationClassBeanDefinitionCollectorChainItem);
    }

    public BeanDefinitionCollector createBeanDefinitionProcessorChain() {
        return new BeanDefinitionCollector(beanDefinitionPreprocessorChain);
    }

    public StereotypeBeanDefinitionCollectorChainItem getStereotypeBeanDefinitionCollectorChainItem() {
        return stereotypeBeanDefinitionCollectorChainItem;
    }

    public ConfigurationClassBeanDefinitionCollectorChainItem getConfigurationClassBeanDefinitionCollectorChainItem() {
        return configurationClassBeanDefinitionCollectorChainItem;
    }

}
