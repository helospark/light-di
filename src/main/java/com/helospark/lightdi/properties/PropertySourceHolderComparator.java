package com.helospark.lightdi.properties;

import java.util.Comparator;

public class PropertySourceHolderComparator implements Comparator<PropertySourceHolder> {

    @Override
    public int compare(PropertySourceHolder first, PropertySourceHolder second) {
        if (first.getOrder() == second.getOrder()) {
            if (first.getProperties().equals(second.getProperties())) {
                return 0;
            } else {
                return first.getProperties().hashCode() > second.getProperties().hashCode() ? 1 : -1;
            }
        } else {
            return first.getOrder() - second.getOrder();
        }
    }

}
