package com.helospark.lightdi.definitioncollector;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.helospark.lightdi.annotation.Bean;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.bean.BeanDependencyDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;

public class ConfigurationClassBeanDefinitionCollectorChainItem implements BeanDefinitionCollectorChainItem {

    @Override
    public List<DependencyDescriptor> collectDefinitions(Class<?> clazz) {
        List<DependencyDescriptor> result = new ArrayList<>();
        StereotypeDependencyDescriptor configurationDescriptor = createConfigurationBeanDescriptor(clazz);
        result.add(configurationDescriptor);
        result.addAll(collectBeanMethodDescriptors(clazz, configurationDescriptor));
        return result;
    }

    private StereotypeDependencyDescriptor createConfigurationBeanDescriptor(Class<?> clazz) {
        return StereotypeDependencyDescriptor.builder()
                .withClazz(clazz)
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
                .withMethod(method)
                .withConfigurationDescriptor(configurationDescriptor)
                .build();
    }

    @Override
    public boolean isSupported(Class<?> clazz) {
        return hasAnnotation(clazz, Configuration.class);
    }

}
