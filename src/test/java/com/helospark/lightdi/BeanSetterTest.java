package com.helospark.lightdi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.it.beansetter.BeanSetterContext;
import com.helospark.lightdi.it.beansetter.NotABean;

public class BeanSetterTest {

    @Test
    public void testBeanSetterAndValuesAreSet() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(BeanSetterContext.class);

        // WHEN
        NotABean bean = context.getBean(NotABean.class);

        // THEN
        assertNotNull(bean.getTestBeanSetAsValue());
        assertNotNull(bean.getTestBeanSetInSetter());
        assertThat(bean.getValue(), is("asd"));
    }

}
