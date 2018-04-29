package com.helospark.lightdi.properties;

import java.util.Map;

public class PropertySourceHolder {
    private int order;
    private Map<String, String> properties;

    public PropertySourceHolder(int order, Map<String, String> properties) {
        this.order = order;
        this.properties = properties;
    }

    public String resolveProperty(String name) {
        return properties.get(name);
    }

    @Override
    public String toString() {
        return "PropertySourceHolder [order=" + order + ", properties=" + properties + "]";
    }

    public int getOrder() {
        return order;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

}
