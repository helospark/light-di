package com.helospark.lightdi.conditional.condition;

import java.util.List;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.ConditionalOnMissingBean;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;

public class MissingBeanDependencyCondition implements DependencyCondition {
    private DependencyDescriptorQuery dependencyDescriptorQuery;
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public MissingBeanDependencyCondition(ConditionalOnMissingBean conditionalOnBean,
            BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        dependencyDescriptorQuery = DependencyDescriptorQuery.builder()
                .withClazz(conditionalOnBean.value())
                .build();
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(LightDiContext context, List<DependencyDescriptor> dependencies) {
        return !beanDependencyConditionEvaluator.isBeanExists(dependencyDescriptorQuery, dependencies);
    }

}
