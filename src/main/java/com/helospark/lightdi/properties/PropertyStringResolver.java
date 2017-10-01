package com.helospark.lightdi.properties;

import java.util.Collection;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyStringResolver {
    private static final String FIRST_NON_ESCAPED_COLON_REGEXP_PATTERN = "(?<!\\\\):";
    private static final String PROPERTY_REGEX = "\\$\\{(.*?)\\}";
    private static final int MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION = 30;
    Pattern pattern = Pattern.compile(PROPERTY_REGEX);
    Pattern defaultValuePattern = Pattern.compile(FIRST_NON_ESCAPED_COLON_REGEXP_PATTERN);
    private PropertyValueResolver propertyValueResolver;

    public PropertyStringResolver(PropertyValueResolver propertyValueResolver) {
        this.propertyValueResolver = propertyValueResolver;
    }

    public String resolve(String value, Collection<PropertySourceHolder> properties) {
        int currentIteration = 0;
        do {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.find()) {
                break;
            }
            String groupValue = matcher.group(1);
            String resolvedValue = resolveProperty(properties, groupValue);
            value = matcher.replaceFirst(Matcher.quoteReplacement(resolvedValue));
            ++currentIteration;
        } while (currentIteration < MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION);
        if (currentIteration >= MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION) {
            throw new IllegalArgumentException(
                    "Property resolution takes too long (maybe recursive properties?), current value: " + value);
        }
        return value;
    }

    private String resolveProperty(Collection<PropertySourceHolder> properties, String groupValue) {
        String valueToResolve = getValueToResolve(groupValue);
        Optional<String> resolvedValue = propertyValueResolver.resolveOptional(valueToResolve, properties);
        if (!resolvedValue.isPresent()) {
            return getDefaultValue(groupValue)
                    .orElseThrow(() -> new IllegalArgumentException("Cannot resolve " + groupValue));
        } else {
            return resolvedValue.get();
        }
    }

    private Optional<String> getDefaultValue(String groupValue) {
        return getDefaultValueSeparatingIndex(groupValue)
                .map(index -> groupValue.substring(index + 1));
    }

    private String getValueToResolve(String groupValue) {
        return getDefaultValueSeparatingIndex(groupValue)
                .map(index -> groupValue.substring(0, index))
                .orElse(groupValue);
    }

    private Optional<Integer> getDefaultValueSeparatingIndex(String value) {
        Matcher matcher = defaultValuePattern.matcher(value);
        if (matcher.find()) {
            return Optional.of(matcher.start());
        } else {
            return Optional.empty();
        }
    }
}
