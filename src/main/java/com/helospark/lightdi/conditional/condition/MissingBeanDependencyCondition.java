package com.helospark.lightdi.conditional.condition;

import com.helospark.lightdi.annotation.ConditionalOnMissingBean;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.util.LightDiAnnotation;

public class MissingBeanDependencyCondition implements DependencyCondition {
    private DependencyDescriptorQuery dependencyDescriptorQuery;
    private BeanDependencyConditionEvaluator beanDependencyConditionEvaluator;

    public MissingBeanDependencyCondition(LightDiAnnotation annotation,
            BeanDependencyConditionEvaluator beanDependencyConditionEvaluator) {
        dependencyDescriptorQuery = DependencyDescriptorQuery.builder()
                .withClazz(annotation.getAttributeAs(ConditionalOnMissingBean.ATTRIBUTE_NAME, Class.class))
                .build();
        this.beanDependencyConditionEvaluator = beanDependencyConditionEvaluator;
    }

    @Override
    public boolean evaluate(ConditionalEvaluationRequest request) {
        return !beanDependencyConditionEvaluator.isBeanExists(dependencyDescriptorQuery, request.dependencies);
    }

}
