package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.specialvaluecontext.SpecialValueWithConstructorInjectConfiguration;
import com.helospark.lightdi.it.specialvaluecontext.SpecialValueWithFieldInjectConfiguration;
import com.helospark.lightdi.it.specialvaluecontext.SpecialValueWithSetterInjectConfiguration;

public class SpecialValueInjectIT {

    @Test
    public void testFieldInject() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(SpecialValueWithFieldInjectConfiguration.class);

        // THEN
        SpecialValueWithFieldInjectConfiguration result = context.getBean(SpecialValueWithFieldInjectConfiguration.class);
        assertThat(result.getDescriptor().getQualifier(), is("specialValueWithFieldInjectConfiguration"));
    }

    @Test
    public void testSetterInject() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(SpecialValueWithSetterInjectConfiguration.class);

        // THEN
        SpecialValueWithSetterInjectConfiguration result = context.getBean(SpecialValueWithSetterInjectConfiguration.class);
        assertThat(result.getDescriptor().getQualifier(), is("specialValueWithSetterInjectConfiguration"));
    }

    @Test
    public void testConstructorInject() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(SpecialValueWithConstructorInjectConfiguration.class);

        // THEN
        SpecialValueWithConstructorInjectConfiguration result = context.getBean(SpecialValueWithConstructorInjectConfiguration.class);
        assertThat(result.getDescriptor().getQualifier(), is("specialValueWithConstructorInjectConfiguration"));
    }

}
