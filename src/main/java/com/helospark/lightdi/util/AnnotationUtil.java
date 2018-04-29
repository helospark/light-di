package com.helospark.lightdi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.helospark.lightdi.annotation.AliasFor;
import com.helospark.lightdi.annotation.RepeatableAnnotationContainer;
import com.helospark.lightdi.cache.SimpleNonThreadSafeCache;
import com.helospark.lightdi.exception.IllegalConfigurationException;

public class AnnotationUtil {

    private static Object cacheLock = new Object();
    private static Set<String> inheritedAnnotationMethodsCache = null;
    private static Map<AnnotatedElement, Set<LightDiAnnotation>> cache = Collections
            .synchronizedMap(new SimpleNonThreadSafeCache<AnnotatedElement, Set<LightDiAnnotation>>(100));

    public static Set<LightDiAnnotation> getAnnotationsOfType(AnnotatedElement parameter, Class<? extends Annotation> annotationClass) {
        Set<LightDiAnnotation> annotations = getAllMergedAnnotations(parameter);
        return annotations.stream()
                .filter(annotation -> annotation.getType().annotationType().equals(annotationClass))
                .collect(Collectors.toSet());
    }

    public static LightDiAnnotation getSingleAnnotationOfType(AnnotatedElement parameter, Class<? extends Annotation> annotationToFind) {
        Set<LightDiAnnotation> annotations = getAnnotationsOfType(parameter, annotationToFind);
        if (annotations.size() != 1) {
            throw new IllegalArgumentException(parameter + " does not contain exactly one " + annotationToFind);
        }
        return annotations.stream().findFirst().get();
    }

    public static boolean hasAnnotation(AnnotatedElement parameter, Class<? extends Annotation> annotation) {
        return getAnnotationsOfType(parameter, annotation).size() > 0;
    }

    public static boolean doesElementContainAnyAnnotationOf(AnnotatedElement method, Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> hasAnnotation(method, annotation))
                .findFirst()
                .isPresent();
    }

    /**
     * Gets all annotation on the given element, including annotations on annotations.
     * It also contains meta annotations (if present) such as Documented or Retention, but deduplicated, if mulitple annotations have it.
     * @param parameter to extract annotations from
     * @return all annotations
     */
    public static Set<LightDiAnnotation> getAllMergedAnnotations(AnnotatedElement parameter) {
        // small cache is added, because the pattern of annotation access usually required 3-4 access one-after another
        Set<LightDiAnnotation> cacheResult = cache.get(parameter);
        if (cacheResult != null) {
            return cacheResult;
        } else {
            Set<LightDiAnnotation> result = new HashSet<>();
            Set<LightDiAnnotation> methodResult = recursivelyMergeAllAnnotationsInternal(parameter, result, new HashMap<>());
            cache.putIfAbsent(parameter, methodResult);
            return methodResult;
        }
    }

    private static Set<LightDiAnnotation> recursivelyMergeAllAnnotationsInternal(AnnotatedElement parameter, Set<LightDiAnnotation> result,
            Map<Class<?>, List<AliasData>> superData) {

        for (Annotation annotation : parameter.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(RepeatableAnnotationContainer.class)) {
                extractRepeatableAnnotations(annotation)
                        .forEach(newAnnotation -> processAnnotation(result, newAnnotation, superData));
            } else {
                processAnnotation(result, annotation, superData);
            }
        }
        return result;
    }

    private static Stream<Annotation> extractRepeatableAnnotations(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        try {
            Method valueMethod = annotationType.getMethod("value");
            Object childAnnotations = valueMethod.invoke(annotation);
            if (!(childAnnotations instanceof Annotation[])) {
                throw new IllegalConfigurationException(
                        "ChildContainer require annotation list as the return type for value() method in " + annotationType);
            }
            return Arrays.stream((Annotation[]) childAnnotations);
        } catch (NoSuchMethodException e) {
            throw new IllegalConfigurationException("AnnotationContainer require value() method in the annotation in class " + annotationType);
        } catch (Exception e) {
            throw new RuntimeException("Unable to extract child annotations in " + annotationType, e);
        }
    }

    private static void processAnnotation(Set<LightDiAnnotation> result, Annotation annotation, Map<Class<?>, List<AliasData>> fromParentAliases) {
        Class<? extends Annotation> type = annotation.annotationType();

        Map<Class<?>, List<AliasData>> superDataCopy = cloneMap(fromParentAliases);

        Map<String, AliasData> aliases = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();
        for (Method method : type.getMethods()) {
            if (isAttributeMethod(method)) {
                String attribute = method.getName();
                Object value = getAnnotationValue(annotation, method);
                Object defaultValue = method.getDefaultValue();
                attributes.put(attribute, value);

                Optional<AliasFor> aliasFor = Optional.ofNullable(method.getAnnotation(AliasFor.class));
                if (aliasFor.isPresent()) {
                    AliasFor alias = aliasFor.get();
                    AliasData aliasData = new AliasData(alias.value(), defaultValue, value);
                    if (alias.annotation() == Annotation.class) {
                        aliases.put(attribute, aliasData);
                    } else {
                        List<AliasData> list = Optional.ofNullable(superDataCopy.get(alias.annotation())).orElse(new ArrayList<>());
                        list.add(aliasData);
                        superDataCopy.put(alias.annotation(), list);
                    }
                }
            }
        }
        checkAliasDataCorrect(aliases);
        for (String key : aliases.keySet()) {
            AliasData thisData = aliases.get(key);
            AliasData otherData = aliases.get(thisData.name);
            Object actualValue = Objects.equals(thisData.actualValue, thisData.defaultValue) ? otherData.actualValue : thisData.actualValue;
            attributes.put(thisData.name, actualValue);
            attributes.put(otherData.name, actualValue);
        }
        if (fromParentAliases.get(type) != null) {
            List<AliasData> aliasData = fromParentAliases.get(type);
            for (AliasData data : aliasData) {
                if (!attributes.containsKey(data.name)) {
                    throw new IllegalConfigurationException("Super annotation attribute referred by @AliasFor does not exist in " + annotation);
                }
                attributes.put(data.name, data.actualValue);
            }
        }
        LightDiAnnotation lightDiAnnotation = LightDiAnnotation.builder()
                .withAttributes(attributes)
                .withType(annotation)
                .build();

        // meta annotations may contain themselves, see @Retention, avoid
        // recursing if it already contains it.
        if (!doesContainItself(result, annotation)) {
            result.add(lightDiAnnotation);
            result.addAll(recursivelyMergeAllAnnotationsInternal(type, result, superDataCopy));
        }
    }

    static boolean isAttributeMethod(Method method) {
        return method.getParameterTypes().length == 0 && !inheritedFromObject(method.getName());
    }

    private static boolean inheritedFromObject(String methodToFind) {
        if (inheritedAnnotationMethodsCache == null) {
            synchronized (cacheLock) {
                if (inheritedAnnotationMethodsCache == null) {
                    inheritedAnnotationMethodsCache = getMethodNamesIn(Annotation.class);
                }
            }
        }
        return inheritedAnnotationMethodsCache.contains(methodToFind);
    }

    private static Set<String> getMethodNamesIn(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .map(method -> method.getName())
                .collect(Collectors.toSet());
    }

    private static Object getAnnotationValue(Annotation annotation, Method method) {
        try {
            return method.invoke(annotation);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get annotation value", e);
        }
    }

    private static Map<Class<?>, List<AliasData>> cloneMap(Map<Class<?>, List<AliasData>> fromParentAliases) {
        Map<Class<?>, List<AliasData>> copyOf = new HashMap<>();
        for (Class<?> key : fromParentAliases.keySet()) {
            copyOf.put(key, new ArrayList<>(fromParentAliases.get(key)));
        }
        return copyOf;
    }

    private static void checkAliasDataCorrect(Map<String, AliasData> aliases) {
        Set<String> keys = aliases.keySet();
        for (String key : keys) {
            AliasData currentAliasData = aliases.get(key);
            String otherName = currentAliasData.name;
            AliasData otherAliasData = aliases.get(otherName);
            if (!Objects.equals(otherAliasData.name, key)) {
                throw new IllegalConfigurationException("@AliasFor should be added to both aliased properties");
            }
            if (!Objects.equals(currentAliasData.defaultValue, otherAliasData.defaultValue)) {
                throw new IllegalConfigurationException("Default value of aliased attributes in the annotation must be equal");
            }
        }
    }

    private static boolean doesContainItself(Set<LightDiAnnotation> result, Annotation annotation) {
        return result.stream()
                .filter(a -> a.getType().equals(annotation))
                .findFirst()
                .isPresent();
    }

    static class AliasData {
        public String name;
        public Object defaultValue;
        public Object actualValue;

        public AliasData(String name, Object defaultValue, Object actualValue) {
            this.name = name;
            this.defaultValue = defaultValue;
            this.actualValue = actualValue;
        }

    }

    public static List<Method> getMethodsWithAnnotation(Class<?> clazz, Class<? extends Annotation>... annotations) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> doesElementContainAnyAnnotationOf(method, annotations))
                .collect(Collectors.toList());
    }

}
