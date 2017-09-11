package com.helospark.lightdi.conditional;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class ConditionalFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalFilter.class);

    public List<DependencyDescriptor> filterDependencies(LightDiContext context, List<DependencyDescriptor> dependencyDescriptors) {
        List<DependencyDescriptor> previousDependencies;
        List<DependencyDescriptor> filteredDependencies = dependencyDescriptors;
        do {
            previousDependencies = filteredDependencies;
            List<DependencyDescriptor> previousDependencies2 = previousDependencies; // TODO: must be effectively final for the lambda
            filteredDependencies = previousDependencies.stream()
                    .filter(descriptor -> evaluateDescriptor(descriptor, context, previousDependencies2))
                    .collect(Collectors.toList());
            LOGGER.debug("Filtering iteration done, remaining number of dependencies: " + filteredDependencies.size());
        } while (filteredDependencies.size() < previousDependencies.size());
        // TODO: This loop is added to handle the conditional on conditional bean case.
        // But it is a bit smelly, conditions should be able to evaluate dependent conditions.
        // A better way has to be done to handle this
        // This also does not handle circular ConditionalOnBeans (though technically keeping both is as correct solution as keeping none).
        // There is no infinite loop, because dependencies will continously decrease in the loop
        return filteredDependencies;
    }

    public boolean evaluateDescriptor(DependencyDescriptor descriptor, LightDiContext context, List<DependencyDescriptor> dependencyDescriptors) {
        boolean result = true;
        for (DependencyCondition condition : descriptor.getConditions()) {
            boolean canditionalValue = condition.evaluate(context, dependencyDescriptors);
            if (!canditionalValue && LOGGER.isDebugEnabled()) {
                LOGGER.debug("Ignoring " + descriptor + " because " + condition + " evaluated to false");
            }
            result &= canditionalValue;
        }
        return result;
    }

}
