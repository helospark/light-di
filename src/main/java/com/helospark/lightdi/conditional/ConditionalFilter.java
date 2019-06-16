package com.helospark.lightdi.conditional;

import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.conditional.condition.ConditionalEvaluationRequest;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ConditionalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalFilter.class);

    public SortedSet<DependencyDescriptor> filterDependencies(LightDiContext context, SortedSet<DependencyDescriptor> dependencyDescriptors) {
        SortedSet<DependencyDescriptor> previousDependencies;
        SortedSet<DependencyDescriptor> filteredDependencies = dependencyDescriptors;

        List<AnnotationBasedConditionalProcessorFactory> customAnnotationProcessors = context.getListOfBeans(AnnotationBasedConditionalProcessorFactory.class);

        do {
            previousDependencies = filteredDependencies;
            SortedSet<DependencyDescriptor> effectivelyFinalDependencies = previousDependencies; // TODO: must be effectively final for the lambda
            filteredDependencies = previousDependencies.stream()
                    .filter(descriptor -> evaluateDescriptor(descriptor, context, effectivelyFinalDependencies, customAnnotationProcessors))
                    .collect(Collectors.toCollection(() -> new TreeSet<>()));
            LOGGER.debug("Filtering iteration done, remaining number of dependencies: " + filteredDependencies.size());
        } while (filteredDependencies.size() < previousDependencies.size());
        // TODO: This loop is added to handle the conditional on conditional bean case.
        // But it is a bit smelly, conditions should be able to evaluate dependent conditions.
        // A better way has to be done to handle this
        // This also does not handle circular ConditionalOnBeans (though technically keeping both is as correct solution as keeping none).
        // There is no infinite loop, because dependencies will continously decrease in the loop
        return filteredDependencies;
    }

    public boolean evaluateDescriptor(DependencyDescriptor descriptor, LightDiContext context,
            SortedSet<DependencyDescriptor> dependencyDescriptors, List<AnnotationBasedConditionalProcessorFactory> customProcessors) {
        ConditionalEvaluationRequest conditionalRequest = ConditionalEvaluationRequest.builder()
                .withContext(context)
                .withDependencies(dependencyDescriptors)
                .withDescriptor(descriptor)
                .build();

        boolean result = true;
        for (DependencyCondition condition : descriptor.getConditions()) {

            boolean canditionalValue = condition.evaluate(conditionalRequest);
            if (!canditionalValue && LOGGER.isDebugEnabled()) {
                LOGGER.debug("Ignoring " + descriptor + " because " + condition + " evaluated to false");
            }
            result &= canditionalValue;
        }
        for (LightDiAnnotation annotation : descriptor.getMergedAnnotations()) {
            Optional<DependencyCondition> optionalAnnotationCondition = customProcessors.stream()
                    .filter(a -> a.getAnnotation().equals(annotation.getType().annotationType()))
                    .findFirst()
                    .map(a -> a.getDependencyCondition(annotation));
            if (optionalAnnotationCondition.isPresent()) {
                DependencyCondition condition = optionalAnnotationCondition.get();
                boolean conditionalValue = condition.evaluate(conditionalRequest);
                if (!conditionalValue && LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Ignoring " + descriptor + " because " + condition + " evaluated to false");
                }
                result &= conditionalValue;
            }
        }
        return result;
    }

}
