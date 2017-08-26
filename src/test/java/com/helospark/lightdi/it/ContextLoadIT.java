package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.testcontext1.ComponentWithConstructorValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithFieldValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithPostConstruct;
import com.helospark.lightdi.it.testcontext1.ComponentWithSetterValue;
import com.helospark.lightdi.it.testcontext1.ConstructorDependency;
import com.helospark.lightdi.it.testcontext1.FieldDependency;
import com.helospark.lightdi.it.testcontext1.ServiceAnnotatedComponent;
import com.helospark.lightdi.it.testcontext1.SetterDependency;

public class ContextLoadIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = lightDi.initContextByPackage(ConstructorDependency.class.getPackage().getName());
    }

    @Test
    public void testConstructorInjectionWorks() {
        // GIVEN

        // WHEN
        ConstructorDependency component = context.getOrCreateBean(ConstructorDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testServiceAnnotationShouldWork() {
        // GIVEN

        // WHEN
        ServiceAnnotatedComponent component = context.getOrCreateBean(ServiceAnnotatedComponent.class);

        // THEN
        assertNotNull(component);
    }

    @Test
    public void testSetterInjectionWorks() {
        // GIVEN

        // WHEN
        SetterDependency component = context
                .getOrCreateBean(SetterDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testFieldInjectionWorks() {
        // GIVEN

        // WHEN
        FieldDependency component = context.getOrCreateBean(FieldDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaConstructor() {
        // GIVEN

        // WHEN
        ComponentWithConstructorValue component = context
                .getOrCreateBean(ComponentWithConstructorValue.class);

        // THEN
        assertThat(component.getValue(), is("asd"));
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaSetter() {
        // GIVEN

        // WHEN
        ComponentWithSetterValue component = context
                .getOrCreateBean(ComponentWithSetterValue.class);

        // THEN
        assertThat(component.getValue(), is("asd=asd"));
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaField() {
        // GIVEN

        // WHEN
        ComponentWithFieldValue component = context
                .getOrCreateBean(ComponentWithFieldValue.class);

        // THEN
        assertThat(component.getValue(), is("value=asd"));
    }

    @Test
    public void testPostConstructShouldWork() {
        // GIVEN

        // WHEN
        ComponentWithPostConstruct component = context
                .getOrCreateBean(ComponentWithPostConstruct.class);

        // THEN
        assertThat(component.getFieldFilledInPostConstruct(), is("ok"));
    }
}
