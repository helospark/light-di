package com.helospark.lightdi.conditional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.helospark.lightdi.conditional.condition.DependencyCondition;

public class ConditionalAnnotationsExtractor {
    private Map<Class<? extends Annotation>, Function<Annotation, DependencyCondition>> converters;

    public ConditionalAnnotationsExtractor(Map<Class<? extends Annotation>, Function<Annotation, DependencyCondition>> converters) {
        this.converters = converters;
    }

    public List<DependencyCondition> extractConditions(Class<?> clazz) {
        return process(Arrays.asList(clazz.getAnnotations()));
    }

    public Collection<? extends DependencyCondition> extractConditions(Method method) {
        return process(Arrays.asList(method.getAnnotations()));
    }

    private List<DependencyCondition> process(List<Annotation> annotations) {
        List<DependencyCondition> result = new ArrayList<>();
        for (Map.Entry<Class<? extends Annotation>, Function<Annotation, DependencyCondition>> entry : converters.entrySet()) {
            Optional<Annotation> annotation = getAnnotation(annotations, entry.getKey());
            if (annotation.isPresent()) {
                result.add(entry.getValue().apply(annotation.get()));
            }
        }
        return result;
    }

    private Optional<Annotation> getAnnotation(List<Annotation> annotations, Class<? extends Annotation> key) {
        return annotations.stream()
                .filter(annotation -> annotation.annotationType().equals(key))
                .findFirst();
    }
}
