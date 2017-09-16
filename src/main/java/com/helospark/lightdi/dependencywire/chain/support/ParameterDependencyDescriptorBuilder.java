package com.helospark.lightdi.dependencywire.chain.support;

import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;

import com.helospark.lightdi.annotation.Qualifier;
import com.helospark.lightdi.annotation.Value;
import com.helospark.lightdi.dependencywire.FindInDependencySupport;
import com.helospark.lightdi.dependencywire.PropertyDescriptorFactory;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.exception.IllegalConfigurationException;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.ReflectionUtil;

public class ParameterDependencyDescriptorBuilder {
    private FindInDependencySupport findInDependencySupport;
    private PropertyDescriptorFactory propertyDescriptorFactory;

    public ParameterDependencyDescriptorBuilder(FindInDependencySupport findInDependencySupport,
            PropertyDescriptorFactory propertyDescriptorFactory) {
        this.findInDependencySupport = findInDependencySupport;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
    }

    public InjectionDescriptor build(Parameter parameter, List<DependencyDescriptor> dependencyDescriptors, boolean required) {
        // TODO: introduce chain here
        if (AnnotationUtil.hasAnnotation(parameter, Value.class)) {
            return propertyDescriptorFactory.buildPropertyDescriptor(parameter);
        } else if (Collection.class.isAssignableFrom(parameter.getType())) {
            DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                    .withClazz(ReflectionUtil.extractFirstGenericType(parameter)
                            .orElseThrow(() -> new IllegalConfigurationException("Collection without generic type")))
                    .build();
            return findInDependencySupport.findListOrEmpty(dependencyDescriptors, query, (Class<? extends Collection<?>>) parameter.getType());
        } else {
            DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                    .withClazz(parameter.getType())
                    .withQualifier(extractQualifierOrNull(parameter))
                    .withRequired(required)
                    .build();
            return findInDependencySupport.find(dependencyDescriptors, query);
        }
    }

    private String extractQualifierOrNull(Parameter parameter) {
        Qualifier[] qualifierAnnotation = parameter.getAnnotationsByType(Qualifier.class);
        if (qualifierAnnotation.length > 0) {
            return qualifierAnnotation[0].value();
        }
        return null;
    }
}
