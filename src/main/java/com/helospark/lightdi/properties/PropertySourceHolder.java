package com.helospark.lightdi.properties;

import java.util.Map;

public class PropertySourceHolder {
    private Map<String, String> properties;

    public PropertySourceHolder(Map<String, String> properties) {
        this.properties = properties;
    }

    public String resolveProperty(String name) {
        return properties.get(name);
    }
}
