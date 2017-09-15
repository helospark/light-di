package com.helospark.lightdi.conditional;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.helospark.lightdi.annotation.ConditionalOnBean;
import com.helospark.lightdi.annotation.ConditionalOnClass;
import com.helospark.lightdi.annotation.ConditionalOnMissingBean;
import com.helospark.lightdi.annotation.ConditionalOnMissingClass;
import com.helospark.lightdi.annotation.ConditionalOnProperty;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.conditional.condition.evaluator.BeanDependencyConditionEvaluator;
import com.helospark.lightdi.conditional.condition.evaluator.ClassDependencyConditionEvaluator;
import com.helospark.lightdi.conditional.condition.factory.ConditionalOnBeanConverter;
import com.helospark.lightdi.conditional.condition.factory.ConditionalOnClassConverter;
import com.helospark.lightdi.conditional.condition.factory.ConditionalOnMissingBeanConverter;
import com.helospark.lightdi.conditional.condition.factory.ConditionalOnMissingClassConverter;
import com.helospark.lightdi.conditional.condition.factory.ConditionalOnPropertyConverter;

public class ConditionalAnnotationsExtractorFactory {

    public ConditionalAnnotationsExtractor createConditionalAnnotationsExtractor() {
        Map<Class<? extends Annotation>, Function<Annotation, DependencyCondition>> converters = new HashMap<>();

        BeanDependencyConditionEvaluator beanDependencyConditionEvaluator = new BeanDependencyConditionEvaluator();
        ClassDependencyConditionEvaluator classDependencyConditionEvaluator = new ClassDependencyConditionEvaluator();

        converters.put(ConditionalOnBean.class, new ConditionalOnBeanConverter(beanDependencyConditionEvaluator));
        converters.put(ConditionalOnClass.class, new ConditionalOnClassConverter(classDependencyConditionEvaluator));

        converters.put(ConditionalOnMissingBean.class, new ConditionalOnMissingBeanConverter(beanDependencyConditionEvaluator));
        converters.put(ConditionalOnMissingClass.class, new ConditionalOnMissingClassConverter(classDependencyConditionEvaluator));

        converters.put(ConditionalOnProperty.class, new ConditionalOnPropertyConverter());

        return new ConditionalAnnotationsExtractor(converters);
    }
}
