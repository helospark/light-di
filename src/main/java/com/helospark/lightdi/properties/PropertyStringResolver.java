package com.helospark.lightdi.properties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyStringResolver {
    private static final String DEFAULT_PROPERTY_VALUE = "";
    private static final String PROPERTY_REGEX = "\\$\\{(.*?)\\}";
    private static final int MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION = 30;
    Pattern pattern = Pattern.compile(PROPERTY_REGEX);
    private PropertyValueResolver propertyValueResolver;

    public PropertyStringResolver(PropertyValueResolver propertyValueResolver) {
        this.propertyValueResolver = propertyValueResolver;
    }

    public String resolve(String value) {
        int currentIteration = 0;
        do {
            Matcher matcher = pattern.matcher(value);
            if (!matcher.find()) {
                break;
            }
            String groupValue = matcher.group(1);
            String resolvedValue = propertyValueResolver.resolveOptional(groupValue).orElse(DEFAULT_PROPERTY_VALUE);
            value = matcher.replaceFirst(Matcher.quoteReplacement(resolvedValue));
            ++currentIteration;
        } while (currentIteration < MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION);
        if (currentIteration >= MAX_ITERATIONS_FOR_PROPERTY_RESOLUTION) {
            throw new IllegalArgumentException(
                    "Property resolution takes too long (maybe recursive properties?), current value: " + value);
        }
        return value;
    }

    private String regexpEscape(String resolvedValue) {
        // TODO Auto-generated method stub
        return null;
    }
}
