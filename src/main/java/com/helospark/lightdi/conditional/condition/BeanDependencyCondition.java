package com.helospark.lightdi.conditional.condition;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class BeanDependencyCondition implements DependencyCondition {
    private DependencyDescriptorQuery dependencyDescriptorQuery;

    public BeanDependencyCondition(ConditionalOnBean conditionalOnBean) {
        dependencyDescriptorQuery = DependencyDescriptorQuery.builder()
                .withClazz(conditionalOnBean.value())
                .build();
    }

    @Override
    public boolean evaluate(LightDiContext context, List<DependencyDescriptor> dependencies) {
        return dependencies.stream()
                .filter(dependency -> dependency.doesMatch(dependencyDescriptorQuery))
                .findFirst()
                .isPresent();
    }

}
