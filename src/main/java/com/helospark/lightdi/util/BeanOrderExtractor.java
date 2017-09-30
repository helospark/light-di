package com.helospark.lightdi.util;

import static com.helospark.lightdi.util.AnnotationUtil.hasAnnotation;

import java.lang.reflect.AnnotatedElement;

import com.helospark.lightdi.annotation.Order;
import com.helospark.lightdi.constants.LightDiConstants;

public class BeanOrderExtractor {

    public static int extractOrder(AnnotatedElement annotatedElement) {
        if (hasAnnotation(annotatedElement, Order.class)) {
            return AnnotationUtil.getSingleAnnotationOfType(annotatedElement, Order.class).value();
        }
        return LightDiConstants.DEFAULT_ORDER;
    }

}
