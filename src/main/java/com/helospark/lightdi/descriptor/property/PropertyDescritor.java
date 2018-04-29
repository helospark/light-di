package com.helospark.lightdi.descriptor.property;

import com.helospark.lightdi.descriptor.GenericClass;
import com.helospark.lightdi.descriptor.InjectionDescriptor;

/**
 * Descriptor represents parameters require for a property injection.
 * @author helospark
 */
public class PropertyDescritor implements InjectionDescriptor {
    private GenericClass clazz;
    private String value;
    private boolean required;

    public PropertyDescritor(GenericClass clazz, String value, boolean required) {
        this.clazz = clazz;
        this.value = value;
        this.required = required;
    }

    public GenericClass getClazz() {
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
