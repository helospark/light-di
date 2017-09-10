package com.helospark.lightdi.properties;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import com.helospark.lightdi.LightDiContext;

public class Environment {
    public static final int DEFAULT_PROPERTY_ORDER = 0;
    public static final int HIGHEST_PROPERTY_ORDER = Integer.MIN_VALUE + 1;
    public static final int LOWEST_PROPERTY_ORDER = Integer.MAX_VALUE;

    private SortedSet<PropertySourceHolder> propertySources;
    private LightDiContext context;

    private ValueResolver valueResolver;

    public Environment(LightDiContext context, ValueResolver valueResolver) {
        this.propertySources = new TreeSet<PropertySourceHolder>(new PropertySourceHolderComparator());
        this.context = context;
        this.valueResolver = valueResolver;
    }

    public String resolve(String value) {
        return valueResolver.resolve(value, String.class, context, propertySources);
    }

    public <T> T resolve(String value, Class<T> type) {
        return valueResolver.resolve(value, type, context, propertySources);
    }

    public void addPropertySources(List<PropertySourceHolder> propertySourceHolders) {
        propertySources.addAll(propertySourceHolders);
    }

    public void addPropertySource(PropertySourceHolder propertySourceHolders) {
        propertySources.add(propertySourceHolders);
    }
}
