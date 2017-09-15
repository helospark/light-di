package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;
import static com.helospark.lightdi.util.BeanNameGenerator.createBeanNameForStereotypeAnnotatedClass;
import static com.helospark.lightdi.util.BeanNameGenerator.createQualifierForMethodAnnotatedWithBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.conditional.ConditionalAnnotationsExtractor;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.util.IsLazyExtractor;
import com.helospark.lightdi.util.IsPrimaryExtractor;
import com.helospark.lightdi.util.QualifierExtractor;

public class ConfigurationClassBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {
    private ConditionalAnnotationsExtractor conditionalAnnotationsExtractor;

    public ConfigurationClassBeanDefinitionCollectorChainItem(ConditionalAnnotationsExtractor conditionalAnnotationsExtractor) {
        this.conditionalAnnotationsExtractor = conditionalAnnotationsExtractor;
    }

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        if (hasAnnotation(clazz, Configuration.class)) {
            List<DependencyDescriptor> result = new ArrayList<>();
            StereotypeDependencyDescriptor configurationDescriptor = createConfigurationBeanDescriptor(clazz);
            result.add(configurationDescriptor);
            result.addAll(collectBeanMethodDescriptors(clazz, configurationDescriptor));
            return result;
        } else {
            return Collections.emptyList();
        }
    }

    private StereotypeDependencyDescriptor createConfigurationBeanDescriptor(Class<?> clazz) {
        return StereotypeDependencyDescriptor.builder()
                .withClazz(clazz)
                .withQualifier(createBeanNameForStereotypeAnnotatedClass(clazz))
                .withScope(QualifierExtractor.extractScope(clazz))
                .withIsLazy(IsLazyExtractor.isLazy(clazz))
                .withIsPrimary(IsPrimaryExtractor.isPrimary(clazz))
                .withConditions(conditionalAnnotationsExtractor.extractConditions(clazz))
                .build();
    }

    private List<DependencyDescriptor> collectBeanMethodDescriptors(Class<?> clazz,
            StereotypeDependencyDescriptor configurationDescriptor) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> hasAnnotation(method, Bean.class))
                .map(method -> createDescriptor(method, configurationDescriptor))
                .collect(Collectors.toList());
    }

    private DependencyDescriptor createDescriptor(Method method,
            StereotypeDependencyDescriptor configurationDescriptor) {
        Class<?> returnType = method.getReturnType();
        return BeanDependencyDescriptor.builder()
                .withClazz(returnType)
                .withScope(QualifierExtractor.extractScope(method))
                .withMethod(method)
                .withQualifier(createQualifierForMethodAnnotatedWithBean(method))
                .withIsLazy(IsLazyExtractor.isLazy(method))
                .withIsPrimary(IsPrimaryExtractor.isPrimary(method))
                .withConfigurationDescriptor(configurationDescriptor)
                .withConditions(getConditions(method, configurationDescriptor))
                .build();
    }

    private List<DependencyCondition> getConditions(Method method, StereotypeDependencyDescriptor configurationDescriptor) {
        List<DependencyCondition> conditions = new ArrayList<>();
        conditions.addAll(configurationDescriptor.getConditions());
        conditions.addAll(conditionalAnnotationsExtractor.extractConditions(method));
        return conditions;
    }

}
