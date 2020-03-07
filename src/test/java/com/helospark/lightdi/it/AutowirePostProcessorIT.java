package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.autowirepostprocessor.AutowirePostProcessorTestConfiguration;
import com.helospark.lightdi.it.autowirepostprocessor.NotABean;
import com.helospark.lightdi.util.AutowirePostProcessor;

public class AutowirePostProcessorIT {

    @Test
    public void test() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(AutowirePostProcessorTestConfiguration.class);
        AutowirePostProcessor autowireUtil = context.getAutowireSupportUtil();
        NotABean notABean = new NotABean(BigDecimal.ONE);

        // WHEN
        autowireUtil.autowireFieldsTo(notABean);

        // THEN
        assertThat(notABean.getBeanName(), is("notABean"));
        assertThat(notABean.getBeanDependency(), is(not(nullValue())));
        assertThat(notABean.getSetterDependency(), is(not(nullValue())));
        assertThat(notABean.getProperty(), is("asd"));
    }

}
