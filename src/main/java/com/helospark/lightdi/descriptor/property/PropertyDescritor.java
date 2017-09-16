package com.helospark.lightdi.descriptor.property;

import com.helospark.lightdi.descriptor.InjectionDescriptor;

public class PropertyDescritor implements InjectionDescriptor {
    private Class<?> clazz;
    private String value;
    private boolean required;

    public PropertyDescritor(Class<?> clazz, String value, boolean required) {
        this.clazz = clazz;
        this.value = value;
        this.required = required;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public String toString() {
        return "PropertyDescritor [clazz=" + clazz + ", value=" + value + ", required=" + required + "]";
    }

}
