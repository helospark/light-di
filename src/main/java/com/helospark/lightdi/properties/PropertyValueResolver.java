package com.helospark.lightdi.properties;

import java.util.Collection;
import java.util.Optional;

public class PropertyValueResolver {

    public Optional<String> resolveOptional(String name, Collection<PropertySourceHolder> properties) {
        return properties.stream()
                .map(holder -> holder.resolveProperty(name))
                .filter(value -> value != null)
                .findFirst();
    }

}
