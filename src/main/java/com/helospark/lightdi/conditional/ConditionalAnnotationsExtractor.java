package com.helospark.lightdi.conditional;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.helospark.lightdi.conditional.condition.DependencyCondition;
import com.helospark.lightdi.util.AnnotationUtil;
import com.helospark.lightdi.util.LightDiAnnotation;

public class ConditionalAnnotationsExtractor {
    private Map<Class<? extends Annotation>, Function<LightDiAnnotation, DependencyCondition>> converters;

    public ConditionalAnnotationsExtractor(Map<Class<? extends Annotation>, Function<LightDiAnnotation, DependencyCondition>> converters) {
        this.converters = converters;
    }

    public List<DependencyCondition> extractConditions(AnnotatedElement annotatedElement) {
        return process(AnnotationUtil.getAllMergedAnnotations(annotatedElement));
    }

    private List<DependencyCondition> process(Collection<LightDiAnnotation> annotations) {
        List<DependencyCondition> result = new ArrayList<>();
        for (Map.Entry<Class<? extends Annotation>, Function<LightDiAnnotation, DependencyCondition>> entry : converters.entrySet()) {
            getAnnotation(annotations, entry.getKey())
                    .stream()
                    .forEach(annotation -> result.add(entry.getValue().apply(annotation)));
        }
        return result;
    }

    private Set<LightDiAnnotation> getAnnotation(Collection<LightDiAnnotation> annotations, Class<? extends Annotation> key) {
        return annotations.stream()
                .filter(annotation -> annotation.getType().annotationType().equals(key))
                .collect(Collectors.toSet());
    }
}
