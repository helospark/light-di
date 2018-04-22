package com.helospark.lightdi.descriptor;

import java.util.Objects;
import java.util.Optional;

public class GenericClass {
    private Class<?> type;
    private Optional<Class<?>> firstGenericType; // TODO: maybe a list in arbitrary depth, usually first is enough

    public GenericClass(Class<?> type, Optional<Class<?>> firstGenericType) {
        this.type = type;
        this.firstGenericType = firstGenericType;
    }

    public GenericClass(Class<?> class1) {
        this.type = class1;
        this.firstGenericType = Optional.empty();
    }

    public Class<?> getType() {
        return type;
    }

    public Optional<Class<?>> getFirstGenericType() {
        return firstGenericType;
    }

    @Override
    public String toString() {
        return "GenericClass [type=" + type + ", firstGenericType=" + firstGenericType + "]";
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof GenericClass)) {
            return false;
        }
        GenericClass castOther = (GenericClass) other;
        return Objects.equals(type, castOther.type) && Objects.equals(firstGenericType, castOther.firstGenericType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, firstGenericType);
    }

}
