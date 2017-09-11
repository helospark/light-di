package com.helospark.lightdi.definitioncollector;

import java.util.Arrays;
import java.util.List;

import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractor;
import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractorFactory;

public class BeanDefinitionProcessorChainFactory {
    private List<BeanDefinitionCollectorChainItem> beanDefinitionPreprocessorChain;
    private StereotypeBeanDefinitionCollectorChainItem stereotypeBeanDefinitionCollectorChainItem;
    private ConfigurationClassBeanDefinitionCollectorChainItem configurationClassBeanDefinitionCollectorChainItem;
    private ImportBeanDefinitionCollectorChainItem importBeanDefinitionCollectorChainItem;

    public BeanDefinitionProcessorChainFactory() {
        ConditionalAnnotationsExtractor conditionalAnnotationsExtractor = new ConditionalAnnotationsExtractorFactory()
                .createConditionalAnnotationsExtractor();
        stereotypeBeanDefinitionCollectorChainItem = new StereotypeBeanDefinitionCollectorChainItem(conditionalAnnotationsExtractor);
        configurationClassBeanDefinitionCollectorChainItem = new ConfigurationClassBeanDefinitionCollectorChainItem(conditionalAnnotationsExtractor);
        importBeanDefinitionCollectorChainItem = new ImportBeanDefinitionCollectorChainItem(
                configurationClassBeanDefinitionCollectorChainItem);
        this.beanDefinitionPreprocessorChain = Arrays.asList(stereotypeBeanDefinitionCollectorChainItem,
                configurationClassBeanDefinitionCollectorChainItem, importBeanDefinitionCollectorChainItem);
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
