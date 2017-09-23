package com.helospark.lightdi.util;

import java.util.Collection;
import java.util.stream.Collectors;

import com.helospark.lightdi.descriptor.DependencyDescriptor;

public class LogMessageBeanNameFormatter {

    public static String convertToBeanNameListString(Collection<DependencyDescriptor> dependencies) {
        return "{ " + createCommaSeparatedList(dependencies) + " }";
    }

    private static String createCommaSeparatedList(Collection<DependencyDescriptor> dependencies) {
        return dependencies.stream()
                .map(dependency -> dependency.getQualifier())
                .collect(Collectors.joining(", "));
    }
}
