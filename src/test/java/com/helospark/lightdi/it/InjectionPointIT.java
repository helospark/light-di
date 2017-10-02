package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptor;
import com.helospark.lightdi.descriptor.InjectionDescriptor;
import com.helospark.lightdi.descriptor.SpecialValueTypeInjectionDescriptor;
import com.helospark.lightdi.descriptor.stereotype.StereotypeDependencyDescriptor;
import com.helospark.lightdi.it.injectionpointpackage.InjectionPointOnConstructorOfSingletonBean;
import com.helospark.lightdi.it.injectionpointpackage.InjectionPointOnFieldOfSingletonBean;
import com.helospark.lightdi.it.injectionpointpackage.InjectionPointOnSetterOfSingletonBean;
import com.helospark.lightdi.it.injectionpointpackage.PrototypeInjectionPointTestConfiguration;
import com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.ClassAForInjectLoggerTest;
import com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.ClassBForInjectLoggerTest;
import com.helospark.lightdi.specialinject.InjectionPoint;

public class InjectionPointIT {
    private LightDi lightDi = new LightDi();
    private LightDiContext context;

    @Test
    public void testDescriptorTypes() {
        // GIVEN
        context = lightDi.initContextByClass(InjectionPointOnFieldOfSingletonBean.class);

        // WHEN
        InjectionPointOnFieldOfSingletonBean result = context.getBean(InjectionPointOnFieldOfSingletonBean.class);

        // THEN
        InjectionPoint injectionPoint = result.getInjectionPoint();
        InjectionDescriptor injectionDescriptor = injectionPoint.getInjectionDescriptor();
        DependencyDescriptor dependencyDescriptor = injectionPoint.getDependencyDescriptor();

        assertThat(injectionDescriptor, instanceOf(SpecialValueTypeInjectionDescriptor.class));
        assertThat(dependencyDescriptor, instanceOf(StereotypeDependencyDescriptor.class));
        assertTrue(((StereotypeDependencyDescriptor) dependencyDescriptor).getClazz().equals((InjectionPointOnFieldOfSingletonBean.class)));
    }

    @Test
    public void testFieldInject() {
        // GIVEN
        context = lightDi.initContextByClass(InjectionPointOnFieldOfSingletonBean.class);

        // WHEN
        InjectionPointOnFieldOfSingletonBean result = context.getBean(InjectionPointOnFieldOfSingletonBean.class);

        // THEN
        InjectionPoint injectionPoint = result.getInjectionPoint();

        assertThat(injectionPoint.getConstructorDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getMethodDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getFieldDescriptor().isPresent(), is(true));
        assertThat(injectionPoint.getFieldDescriptor().get().getField().getName(), is("injectionPoint"));
    }

    @Test
    public void testConstructorInject() {
        // GIVEN
        context = lightDi.initContextByClass(InjectionPointOnConstructorOfSingletonBean.class);

        // WHEN
        InjectionPointOnConstructorOfSingletonBean result = context.getBean(InjectionPointOnConstructorOfSingletonBean.class);

        // THEN
        InjectionPoint injectionPoint = result.getInjectionPoint();

        assertThat(injectionPoint.getConstructorDescriptor().isPresent(), is(true));
        assertThat(injectionPoint.getMethodDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getFieldDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getConstructorDescriptor().get().getIndex(), is(0));
    }

    @Test
    public void testSetterInject() {
        // GIVEN
        context = lightDi.initContextByClass(PrototypeInjectionPointTestConfiguration.class);

        // WHEN
        ClassAForInjectLoggerTest classAForInjectLoggerTest = context.getBean(ClassAForInjectLoggerTest.class);
        ClassBForInjectLoggerTest classBForInjectLoggerTest = context.getBean(ClassBForInjectLoggerTest.class);

        // THEN
        assertThat(classAForInjectLoggerTest.getLogger().getName(),
                is("com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.ClassAForInjectLoggerTest"));
        assertThat(classBForInjectLoggerTest.getLogger().getName(),
                is("com.helospark.lightdi.it.injectionpointpackage.loggerinjecttest.ClassBForInjectLoggerTest"));
    }

    @Test
    public void testPrototypeInjectionPoint() {
        // GIVEN
        context = lightDi.initContextByClass(InjectionPointOnSetterOfSingletonBean.class);

        // WHEN
        InjectionPointOnSetterOfSingletonBean result = context.getBean(InjectionPointOnSetterOfSingletonBean.class);

        // THEN
        InjectionPoint injectionPoint = result.getInjectionPoint();

        assertThat(injectionPoint.getConstructorDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getMethodDescriptor().isPresent(), is(true));
        assertThat(injectionPoint.getFieldDescriptor().isPresent(), is(false));
        assertThat(injectionPoint.getMethodDescriptor().get().getMethod().getName(), is("setInjectionPoint"));
    }
}
