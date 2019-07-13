package com.helospark.lightdi.it.testcontext1;

import java.util.List;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Value;

@Component
public class ComponentWithCollectionValue {
    @Value("${SPACE_SEPARATED_ARRAY}")
    private String[] stringArray;
    @Value("${INT_ARRAY}")
    private Integer[] intArray;
    @Value("${INT_ARRAY}")
    private List<Integer> intArrayAsCollection;
    @Value(value = "${NOT_EXISTENT_STRING_ARRAY}", required = false)
    private String[] notExistentArray;

    private String[] nonExistentArrayFromConstructor;
    private String[] nonExistentArrayFromSetter;

    public ComponentWithCollectionValue(@Value(value = "${NON_EXISTENT_ARRAY_TO_CONSTUCTOR}", required = false) String[] nonExistentArrayFromConstructor) {
        this.nonExistentArrayFromConstructor = nonExistentArrayFromConstructor;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public Integer[] getIntArray() {
        return intArray;
    }

    public List<Integer> getIntArrayAsCollection() {
        return intArrayAsCollection;
    }

    public String[] getNotExistentArray() {
        return notExistentArray;
    }

    public String[] getNonExistentArrayFromConstructor() {
        return nonExistentArrayFromConstructor;
    }

    public void setNonExistentArrayFromConstructor(String[] nonExistentArrayFromConstructor) {
        this.nonExistentArrayFromConstructor = nonExistentArrayFromConstructor;
    }

    public String[] getNonExistentArrayFromSetter() {
        return nonExistentArrayFromSetter;
    }

    @Value(value = "${NON_EXISTENT_ARRAY_TO_SETTER}", required = false)
    public void setNonExistentArrayFromSetter(String[] nonExistentArrayFromSetter) {
        this.nonExistentArrayFromSetter = nonExistentArrayFromSetter;
    }

}
