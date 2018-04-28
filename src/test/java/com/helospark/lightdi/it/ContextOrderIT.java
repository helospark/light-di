package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.ordertest.CommonOrderInterface;
import com.helospark.lightdi.it.ordertest.FirstPrimaryBean;

public class ContextOrderIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = LightDi.initContextByPackage(CommonOrderInterface.class.getPackage().getName());
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void testCollectionOrderShouldBeCorrect() {
        // GIVEN

        // WHEN
        List<CommonOrderInterface> beans = context.getListOfBeans(CommonOrderInterface.class);

        // THEN
        assertThat(beans.get(0).getOrder(), is(1));
        assertThat(beans.get(1).getOrder(), is(2));
        assertThat(beans.get(2).getOrder(), is(3));
    }

    @Test
    public void testPrimaryShouldGetHighestPriorityBean() {
        // GIVEN

        // WHEN
        Object bean = context.getBean("qualified");

        // THEN
        assertThat(bean, instanceOf(FirstPrimaryBean.class));
    }
}
