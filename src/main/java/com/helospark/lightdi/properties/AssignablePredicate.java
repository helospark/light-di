package com.helospark.lightdi.properties;

import java.util.HashMap;
import java.util.Map;

public class AssignablePredicate {
    private Map<Class<?>, Class<?>> primitiveWrapperMap;

    public AssignablePredicate() {
        primitiveWrapperMap = new HashMap<>();
        primitiveWrapperMap.put(boolean.class, Boolean.class);
        primitiveWrapperMap.put(byte.class, Byte.class);
        primitiveWrapperMap.put(char.class, Character.class);
        primitiveWrapperMap.put(double.class, Double.class);
        primitiveWrapperMap.put(float.class, Float.class);
        primitiveWrapperMap.put(int.class, Integer.class);
        primitiveWrapperMap.put(long.class, Long.class);
        primitiveWrapperMap.put(short.class, Short.class);
        primitiveWrapperMap.put(void.class, Void.class);
    }

    public boolean canBeAssigned(Class<?> from, Class<?> to) {
        if (to.isAssignableFrom(from)) {
            return true;
        }
        if (to.isPrimitive()) {
            to = primitiveWrapperMap.get(to);
        }
        if (from.isPrimitive()) {
            from = primitiveWrapperMap.get(from);
        }

        return to.isAssignableFrom(from);
    }
}
