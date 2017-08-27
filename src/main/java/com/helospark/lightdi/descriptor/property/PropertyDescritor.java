package com.helospark.lightdi.descriptor.property;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class PropertyDescritor implements InjectionDescriptor {
    private Class<?> clazz;
    private String value;

    public PropertyDescritor(Class<?> clazz, String value) {
        this.clazz = clazz;
        this.value = value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getValue() {
        return value;
    }

}
