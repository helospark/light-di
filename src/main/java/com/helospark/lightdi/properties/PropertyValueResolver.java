package com.helospark.lightdi.properties;

import java.util.Collection;
import java.util.Optional;

public class PropertyValueResolver {
    private static final String FIRST_NON_ESCAPED_COLON_REGEXP_PATTERN = ":<!\\";

    public Optional<String> resolveOptional(String name, Collection<PropertySourceHolder> properties) {
        return properties.stream()
                .map(holder -> holder.resolveProperty(name))
                .filter(value -> value != null)
                .findFirst();
    }

}
