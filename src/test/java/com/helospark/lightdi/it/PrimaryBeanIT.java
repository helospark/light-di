package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.multiprimary.MultiplePrimaryConfiguration;
import com.helospark.lightdi.it.multiprimary.MultiplePrimaryConfiguration.MultiplePrimaryBean;

public class PrimaryBeanIT {

    @Test
    public void testMultiplePrimaryShouldFindOneWithHighestOrder() {
        // GIVEN

        // WHEN
        LightDiContext context = LightDi.initContextByClass(MultiplePrimaryConfiguration.class);

        // THEN
        assertThat(context.getBean(MultiplePrimaryBean.class).getName(), is("firstPrimary"));
    }

}
