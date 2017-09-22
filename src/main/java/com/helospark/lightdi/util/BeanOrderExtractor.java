package com.helospark.lightdi.util;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Order;
import com.helospark.lightdi.constants.LightDiConstants;

public class BeanOrderExtractor {

    public static int extractOrder(AnnotatedElement annotatedElement) {
        Order order = annotatedElement.getAnnotation(Order.class);
        if (order != null) {
            return order.value();
        }
        return LightDiConstants.DEFAULT_ORDER;
    }

}
