package com.helospark.lightdi.properties;

/**
 * Converts property from String to the given generic type.
 * <p>
 * Required converter will automatically will be chosen by the type the value is injected into
 * @author helospark
 *
 * @param <T> to convert property to
 */
public interface PropertyConverter<T> {

    /**
     * Convert the given resolved property value to the given generic type.
     * @param property resolved property value
     * @return converted result
     */
    public T convert(String property);
}
