package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.testcontext1.ComponentWithConstructorValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithFieldValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithPostConstruct;
import com.helospark.lightdi.it.testcontext1.ComponentWithQualifiedDependency;
import com.helospark.lightdi.it.testcontext1.ComponentWithSetterValue;
import com.helospark.lightdi.it.testcontext1.ConfigurationClass;
import com.helospark.lightdi.it.testcontext1.ConstructorDependency;
import com.helospark.lightdi.it.testcontext1.FieldDependency;
import com.helospark.lightdi.it.testcontext1.NonAnnotatedClass;
import com.helospark.lightdi.it.testcontext1.OtherNonAnnotatedClass;
import com.helospark.lightdi.it.testcontext1.QualifiedBean;
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
        ConstructorDependency component = context.getBean(ConstructorDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testServiceAnnotationShouldWork() {
        // GIVEN

        // WHEN
        ServiceAnnotatedComponent component = context.getBean(ServiceAnnotatedComponent.class);

        // THEN
        assertNotNull(component);
    }

    @Test
    public void testSetterInjectionWorks() {
        // GIVEN

        // WHEN
        SetterDependency component = context
                .getBean(SetterDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testFieldInjectionWorks() {
        // GIVEN

        // WHEN
        FieldDependency component = context.getBean(FieldDependency.class);

        // THEN
        assertNotNull(component);
        assertNotNull(component.getRootDependency());
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaConstructor() {
        // GIVEN

        // WHEN
        ComponentWithConstructorValue component = context
                .getBean(ComponentWithConstructorValue.class);

        // THEN
        assertThat(component.getValue(), is("asd"));
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaSetter() {
        // GIVEN

        // WHEN
        ComponentWithSetterValue component = context
                .getBean(ComponentWithSetterValue.class);

        // THEN
        assertThat(component.getValue(), is("asd=asd"));
    }

    @Test
    public void testValueShouldInjectFromPropertiesFileViaField() {
        // GIVEN

        // WHEN
        ComponentWithFieldValue component = context
                .getBean(ComponentWithFieldValue.class);

        // THEN
        assertThat(component.getValue(), is("value=asd"));
    }

    @Test
    public void testPostConstructShouldWork() {
        // GIVEN

        // WHEN
        ComponentWithPostConstruct component = context
                .getBean(ComponentWithPostConstruct.class);

        // THEN
        assertThat(component.getFieldFilledInPostConstruct(), is("ok"));
    }

    @Test
    public void testConfigurationClassShouldWork() {
        // GIVEN

        // WHEN
        ConfigurationClass configurationClass = context.getBean(ConfigurationClass.class);
        NonAnnotatedClass nonAnnotatedClass = context.getBean(NonAnnotatedClass.class);

        // THEN
        assertNotNull(configurationClass);
        assertNotNull(nonAnnotatedClass);
    }

    @Test
    public void testConfigurationClassShouldWorkWithDependency() {
        // GIVEN

        // WHEN
        OtherNonAnnotatedClass otherNonAnnotatedClass = context.getBean(OtherNonAnnotatedClass.class);

        // THEN
        assertNotNull(otherNonAnnotatedClass);
        assertNotNull(otherNonAnnotatedClass.getDependency());
    }

    @Test
    public void testQualifiedBeanShouldWork() {
        // GIVEN

        // WHEN
        QualifiedBean qualifiedBean = (QualifiedBean) context.getBean("myQualifiedBean");
        QualifiedBean otherQualifiedBean = (QualifiedBean) context.getBean("otherQualifiedBean");

        // THEN
        assertNotNull(qualifiedBean);
        assertNotNull(otherQualifiedBean);
        assertNotEquals(qualifiedBean, otherQualifiedBean);
    }

    @Test
    public void testQualifiedDependencyShouldWork() {
        // GIVEN

        // WHEN
        ComponentWithQualifiedDependency qualifiedBean = context.getBean(ComponentWithQualifiedDependency.class);
        QualifiedBean otherQualifiedBean = (QualifiedBean) context.getBean("myQualifiedBean");

        // THEN
        assertNotNull(qualifiedBean);
        assertEquals(qualifiedBean.getQualifiedBean(), otherQualifiedBean);
    }
}
