package com.helospark.lightdi.properties;

public interface PropertyConverter<T> {

    public T convert(String property);
}
