package com.helospark.lightdi.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.Test;

import com.helospark.lightdi.annotation.AliasFor;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.RepeatableAnnotationContainer;
import com.helospark.lightdi.annotation.Service;
import com.helospark.lightdi.exception.IllegalConfigurationException;

public class AnnotationUtilTest {

    @Test
    public void testGetMethodsAnnotatedWith() throws NoSuchMethodException, SecurityException {
        // GIVEN

        // WHEN
        List<Method> result = AnnotationUtil.getMethodsWithAnnotation(TestClassWithAnnotation.class, PostConstruct.class);

        // THEN
        assertThat(result, is(Collections.singletonList(TestClassWithAnnotation.class.getMethod("annotatedMethod"))));
    }

    @Test
    public void testGetSingleAnnotationOfTypeShouldReturnExpected() {
        // GIVEN

        // WHEN
        LightDiAnnotation component = AnnotationUtil.getSingleAnnotationOfType(AnnotatedClass.class, Component.class);

        // THEN
        assertThat(component.getAttributeAs("value", String.class), is("component"));
    }

    @Test
    public void testExtractAnnotationReturnsAResult() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(LocalAliasForTestWithNameGiven.class,
                LocalAliasForTestAnnotation.class);

        // THEN
        assertThat(result.size(), is(1));
    }

    @Test
    public void testLocalAliasForWorks() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(LocalAliasForTestWithNameGiven.class,
                LocalAliasForTestAnnotation.class);

        LightDiAnnotation foundAnnotation = result.stream().findFirst().get();

        // THEN
        assertThat(foundAnnotation.getAttributeValue("name"), is("testName"));
        assertThat(foundAnnotation.getAttributeValue("value"), is("testName"));
    }

    @Test
    public void testLocalAliasShouldReturnTwoAttributes() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(LocalAliasForTestWithNameGiven.class,
                LocalAliasForTestAnnotation.class);

        LightDiAnnotation foundAnnotation = result.stream().findFirst().get();

        // THEN
        assertThat(foundAnnotation.getAttributes().size(), is(2));
    }

    @Test
    public void testLocalAliasForWithValueGiven() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(LocalAliasForTestWithValueGiven.class,
                LocalAliasForTestAnnotation.class);

        LightDiAnnotation foundAnnotation = result.stream().findFirst().get();

        // THEN
        assertThat(foundAnnotation.getAttributeValue("name"), is("testValue"));
        assertThat(foundAnnotation.getAttributeValue("value"), is("testValue"));
    }

    @Test
    public void testLocalAliasForWithNoValueGiven() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(LocalAliasForTestWithNothingGiven.class,
                LocalAliasForTestAnnotation.class);

        LightDiAnnotation foundAnnotation = result.stream().findFirst().get();

        // THEN
        assertThat(foundAnnotation.getAttributeValue("name"), is(""));
        assertThat(foundAnnotation.getAttributeValue("value"), is(""));
    }

    @Test
    public void testSuperAliasFor() {
        // GIVEN

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(PropertySourceTest.class,
                PropertySource.class);

        LightDiAnnotation foundAnnotation = result.stream().findFirst().get();

        // THEN
        assertThat(foundAnnotation.getAttributeValue("value"), is(new String[] { "this_shall_be_the_value" }));
    }

    @Test
    public void testRepeatableAnnotation() {
        // GIVEN
        Set<String> expected = new HashSet<>();
        expected.add("one");
        expected.add("two");

        // WHEN
        Set<LightDiAnnotation> result = AnnotationUtil.getAnnotationsOfType(AnnotationContainerTest.class,
                RepetableAnnotation.class);

        // THEN
        List<LightDiAnnotation> annotations = new ArrayList<>(result);
        assertThat(annotations.size(), is(2));
        HashSet<String> set = new HashSet<>();

        Set<String> values = annotations.stream()
                .map(e -> e.getAttributeValue("value"))
                .map(e -> (String) e)
                .collect(Collectors.toSet());
        assertThat(values, is(expected));
    }

    @Test(expected = IllegalConfigurationException.class)
    public void testNoMetaAnnotationField() {
        // GIVEN

        // WHEN
        AnnotationUtil.getAnnotationsOfType(NonExistentMetaAnnotationAttribute.class,
                MetaAnnotationAttributeDoesNotExist.class);

        // THEN throws
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSingleAnnotation() {
        // GIVEN

        // WHEN
        AnnotationUtil.getSingleAnnotationOfType(AnnotationContainerTest.class,
                RepeatableAnnotation.class);

        // THEN throws
    }

    @LocalAliasForTestAnnotation(name = "testName")
    static class LocalAliasForTestWithNameGiven {

    }

    @LocalAliasForTestAnnotation(value = "testValue")
    static class LocalAliasForTestWithValueGiven {

    }

    @LocalAliasForTestAnnotation
    static class LocalAliasForTestWithNothingGiven {

    }

    @UnitTestTestPropertySource(location = "this_shall_be_the_value")
    static class PropertySourceTest {

    }

    @RepetableAnnotations({
            @RepetableAnnotation("one"),
            @RepetableAnnotation("two")
    })
    static class AnnotationContainerTest {

    }

    @MetaAnnotationAttributeDoesNotExist
    static class NonExistentMetaAnnotationAttribute {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @PropertySource(value = "DOES_NOT_MATTER")
    public @interface UnitTestTestPropertySource {
        @AliasFor(value = "value", annotation = PropertySource.class)
        String[] location() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @PropertySource(value = "DOES_NOT_MATTER")
    public @interface MetaAnnotationAttributeDoesNotExist {
        @AliasFor(value = "doesNotExists", annotation = PropertySource.class)
        String[] location() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    public @interface LocalAliasForTestAnnotation {
        @AliasFor("value")
        String name() default "";

        @AliasFor("name")
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(value = RepetableAnnotations.class)
    @Documented
    public @interface RepetableAnnotation {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @RepeatableAnnotationContainer
    @Documented
    public @interface RepetableAnnotations {
        RepetableAnnotation[] value();
    }

    private List<LightDiAnnotation> getAnnotationEqualToClass(Set<LightDiAnnotation> result, Class<? extends Annotation> class1) {
        return result.stream()
                .filter(annotation -> annotation.getType().annotationType().equals(class1))
                .collect(Collectors.toList());
    }

    static class TestClassWithAnnotation {
        @PostConstruct
        public void annotatedMethod() {

        }
    }

    @Component("component")
    @Service("service")
    @RepeatableAnnotation("a")
    @RepeatableAnnotation("b")
    static class AnnotatedClass {
    }

    @Repeatable(RepeatableAnnotations.class)
    @Retention(RetentionPolicy.RUNTIME)
    @interface RepeatableAnnotation {

        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface RepeatableAnnotations {
        RepeatableAnnotation[] value();
    }
}
