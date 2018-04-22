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
    //    @Value("${SPACE_SEPARATED_INT_ARRAY}")
    private int[] spaceSeparatedIntArray;
    @Value("${INT_ARRAY}")
    private List<Integer> intArrayAsCollection;

    public String[] getStringArray() {
        return stringArray;
    }

    public Integer[] getIntArray() {
        return intArray;
    }

    public int[] getSpaceSeparatedIntArray() {
        return spaceSeparatedIntArray;
    }

    public List<Integer> getIntArrayAsCollection() {
        return intArrayAsCollection;
    }

}
