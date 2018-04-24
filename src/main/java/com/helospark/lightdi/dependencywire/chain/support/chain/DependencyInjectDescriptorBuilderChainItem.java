package com.helospark.lightdi.dependencywire.chain.support.chain;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Qualifier;
import com.helospark.lightdi.dependencywire.FindInDependencySupport;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.util.AnnotationUtil;

public class DependencyInjectDescriptorBuilderChainItem implements InjectDescriptorBuilderChainItem {
    private FindInDependencySupport findInDependencySupport;

    public DependencyInjectDescriptorBuilderChainItem(FindInDependencySupport findInDependencySupport) {
        this.findInDependencySupport = findInDependencySupport;
    }

    @Override
    public InjectionDescriptor build(InjectDescriptorBuilderRequest request) {
        DependencyDescriptorQuery query = DependencyDescriptorQuery.builder()
                .withClazz(request.getType())
                .withQualifier(extractQualifierOrNull(request.getParameter()))
                .withRequired(request.isRequired())
                .build();
        return findInDependencySupport.find(request.getDependencyDescriptors(), query);
    }

    @Override
    public boolean doesSupport(InjectDescriptorBuilderRequest request) {
        return true;
    }

    private String extractQualifierOrNull(AnnotatedElement parameter) {
        return AnnotationUtil.getAnnotationsOfType(parameter, Qualifier.class)
                .stream()
                .findFirst()
                .map(ann -> ann.getAttributeAs(Qualifier.ATTRIBUTE_NAME, String.class))
                .orElse(null);
    }
}
