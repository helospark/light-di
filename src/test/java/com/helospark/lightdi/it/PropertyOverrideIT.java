package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.propertyoverride.BeanWithProperty;

public class PropertyOverrideIT {

    @Test
    public void testValueShouldBeTheOverridden() {
        // GIVEN
        LightDiContext lightDi = LightDi.initContextUsing("com.helospark.lightdi.it.propertyoverride", this.getClass());

        // WHEN
        BeanWithProperty bean = lightDi.getBean(BeanWithProperty.class);

        // THEN
        assertThat(bean.getValue(), is("overridden"));
    }
}
