package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.othertestpackage.TestComponentInDifferentPackage;
import com.helospark.lightdi.it.testcontext1.ComponentWithCollectionValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithConstructorValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithFieldValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithNoEagernessAnnotation;
import com.helospark.lightdi.it.testcontext1.ComponentWithPostConstruct;
import com.helospark.lightdi.it.testcontext1.ComponentWithPrimitiveValue;
import com.helospark.lightdi.it.testcontext1.ComponentWithQualifiedDependency;
import com.helospark.lightdi.it.testcontext1.ComponentWithSetterValue;
import com.helospark.lightdi.it.testcontext1.ConfigurationClass;
import com.helospark.lightdi.it.testcontext1.ConstructorDependency;
import com.helospark.lightdi.it.testcontext1.ContextAwareTest;
import com.helospark.lightdi.it.testcontext1.EagerComponent;
import com.helospark.lightdi.it.testcontext1.EnvironmentInjectTest;
import com.helospark.lightdi.it.testcontext1.FieldDependency;
import com.helospark.lightdi.it.testcontext1.LazyComponent;
import com.helospark.lightdi.it.testcontext1.ManuallyRegisteredBean;
import com.helospark.lightdi.it.testcontext1.MultiConstructorServer;
import com.helospark.lightdi.it.testcontext1.NonAnnotatedClass;
import com.helospark.lightdi.it.testcontext1.OtherNonAnnotatedClass;
import com.helospark.lightdi.it.testcontext1.PrimaryTestBean;
import com.helospark.lightdi.it.testcontext1.PrototypeBean;
import com.helospark.lightdi.it.testcontext1.QualifiedBean;
import com.helospark.lightdi.it.testcontext1.RequiredFalseFieldDependency;
import com.helospark.lightdi.it.testcontext1.RequiredFalseSetterDependency;
import com.helospark.lightdi.it.testcontext1.RequiredFalseValueMissing;
import com.helospark.lightdi.it.testcontext1.ServiceAnnotatedComponent;
import com.helospark.lightdi.it.testcontext1.SetterDependency;
import com.helospark.lightdi.it.testcontext1.TestDependency.InnerClass;
import com.helospark.lightdi.it.thirdpackage.ThirdPackageConfiguration;

public class ContextLoadIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        context = LightDi.initContextByPackage(ConstructorDependency.class.getPackage().getName());
    }

    @After
    public void tearDown() throws Exception {
        context.close();
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
    public void testValueShouldInjectWhenAutoboxingCanBeDone() {
        // GIVEN

        // WHEN
        ComponentWithPrimitiveValue component = context
                .getBean(ComponentWithPrimitiveValue.class);

        // THEN
        assertThat(component.getIntValue(), is(1));
        assertThat(component.getLongValue(), is(1L));
        assertThat(component.isBoolValue(), is(true));
    }

    @Test
    public void testValueOnCollections() {
        // GIVEN

        // WHEN
        ComponentWithCollectionValue bean = context.getBean(ComponentWithCollectionValue.class);

        // THEN
        assertThat(bean.getIntArray(), is(new Integer[]{1, 2, 3}));
        assertThat(bean.getIntArrayAsCollection(), is(Arrays.asList(1, 2, 3)));
        assertThat(bean.getStringArray(), is(new String[]{"a", "b", "c"}));
        assertThat(bean.getNonExistentArrayFromSetter(), is(nullValue()));
        assertThat(bean.getNonExistentArrayFromConstructor(), is(nullValue()));
        assertThat(bean.getNotExistentArray(), is(nullValue()));
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

    @Test
    public void testPrototypeBeanShouldCreateNewInstancesForEachGetBean() {
        // GIVEN

        // WHEN
        PrototypeBean firstInstance = context.getBean(PrototypeBean.class);
        PrototypeBean secondInstance = context.getBean(PrototypeBean.class);

        // THEN
        assertNotSame(firstInstance, secondInstance);
        assertSame(firstInstance.getTestDependency(), secondInstance.getTestDependency());
    }

    @Test
    public void testEagerComponentShouldInitializeWhenContextLoads() {
        // GIVEN

        // WHEN context loads

        // THEN
        assertTrue(EagerComponent.IS_INITIALIZED);
    }

    @Test
    public void testLazyComponentShouldNotBeInitializeWhenContextLoads() {
        // GIVEN

        // WHEN context loads

        // THEN
        assertFalse(LazyComponent.IS_INITIALIZED);
    }

    @Test
    public void testLazyComponentShouldBeInitializedAfterExplicitlyRequestingIt() {
        // GIVEN

        // WHEN
        LazyComponent instance = context.getBean(LazyComponent.class);

        // THEN
        assertNotNull(instance);
        assertTrue(LazyComponent.IS_INITIALIZED);
    }

    @Test
    public void testComponentWithoutEagernessAnnotationShouldBeLazyByDefault() {
        // GIVEN

        // WHEN context loads

        // THEN
        assertFalse(ComponentWithNoEagernessAnnotation.IS_INITIALIZED);
    }

    @Test
    public void testPrimaryBeanShouldGetPrimary() {
        // GIVEN

        // WHEN
        PrimaryTestBean instance = context.getBean(PrimaryTestBean.class);

        // THEN
        assertEquals(instance.getValue(), "primary");
    }

    @Test
    public void testComponentScanShouldWork() {
        // GIVEN

        // WHEN
        TestComponentInDifferentPackage instance = context.getBean(TestComponentInDifferentPackage.class);

        // THEN
        assertNotNull(instance);
    }

    @Test
    public void testImportShouldWork() {
        // GIVEN

        // WHEN
        ThirdPackageConfiguration instance = context.getBean(ThirdPackageConfiguration.class);

        // THEN
        assertNotNull(instance);
    }

    @Test
    public void testContextAwareShouldHaveContext() {
        // GIVEN

        // WHEN
        ContextAwareTest instance = context.getBean(ContextAwareTest.class);

        // THEN
        assertThat(instance.getContext(), is(context));
    }

    @Test
    public void testRegisterSingletonShouldRegisterBean() {
        // GIVEN
        ManuallyRegisteredBean bean = new ManuallyRegisteredBean();
        context.registerSingleton(bean);

        // WHEN
        ManuallyRegisteredBean instance = context.getBean(ManuallyRegisteredBean.class);

        // THEN
        assertThat(instance, is(bean));
    }

    @Test
    public void testEnvironmentInject() {
        // GIVEN

        // WHEN
        EnvironmentInjectTest bean = context.getBean(EnvironmentInjectTest.class);

        // THEN
        assertThat(bean.getValue(), is("asd"));
    }

    @Test
    public void testRequiredFalseDependencyOnFieldShouldBeNull() {
        // GIVEN

        // WHEN
        RequiredFalseFieldDependency bean = context.getBean(RequiredFalseFieldDependency.class);

        // THEN
        assertNull(bean.getNotABean());
    }

    @Test
    public void testRequiredFalseDependencyOnSetterShouldBeNull() {
        // GIVEN

        // WHEN
        RequiredFalseSetterDependency bean = context.getBean(RequiredFalseSetterDependency.class);

        // THEN
        assertNull(bean.getNotABean());
    }

    @Test
    public void testRequiredFalseValueShouldBeEmpty() {
        // GIVEN

        // WHEN
        RequiredFalseValueMissing bean = context.getBean(RequiredFalseValueMissing.class);

        // THEN
        assertThat(bean.getValue(), is(nullValue()));
    }

    @Test
    public void testInnerClassShouldWork() {
        // GIVEN

        // WHEN
        InnerClass bean = context.getBean(InnerClass.class);

        // THEN
        assertNotNull(bean);
    }

    @Test
    public void testMultiConstructorWithSingleAutowired() {
        // GIVEN

        // WHEN
        MultiConstructorServer bean = context.getBean(MultiConstructorServer.class);

        // THEN
        assertNotNull(bean.getTestDependency());
        assertThat(bean.getValue(), is("setFromConstructor"));
    }
}
