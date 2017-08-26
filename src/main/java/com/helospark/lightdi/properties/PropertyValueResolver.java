package com.helospark.lightdi.properties;

import java.util.List;
import java.util.Optional;

public class PropertyValueResolver {
    private List<PropertySourceHolder> properties;

    public PropertyValueResolver(List<PropertySourceHolder> properties) {
        this.properties = properties;
    }

    public Optional<String> resolveOptional(String name) {
        return properties.stream()
                .map(holder -> holder.resolveProperty(name))
                .filter(value -> value != null)
                .findFirst();
    }
}
