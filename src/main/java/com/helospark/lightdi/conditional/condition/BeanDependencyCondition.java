package com.helospark.lightdi.conditional.condition;

import java.util.SortedSet;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class BeanDependencyCondition implements DependencyCondition {
    private DependencyDescriptorQuery dependencyDescriptorQuery;
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public BeanDependencyCondition(ConditionalOnBean conditionalOnBean,
            BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        dependencyDescriptorQuery = DependencyDescriptorQuery.builder()
                .withClazz(conditionalOnBean.value())
                .build();
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(LightDiContext context, SortedSet<DependencyDescriptor> dependencies) {
        return beanDependencyConditionEvaluator.isBeanExists(dependencyDescriptorQuery, dependencies);
    }

}
