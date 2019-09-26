package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.beanlist.BeanClass;
import com.helospark.lightdi.it.beanlist.BeanListInjected;
import com.helospark.lightdi.it.beanlist.MergeBeanListConfiguration;

public class MergeBeanListIT {

    @Test
    public void testBeanListWithGetBeanList() {
        // GIVEN
        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("a");
        expectedSet.add("b");
        expectedSet.add("c");
        expectedSet.add("d");
        expectedSet.add("e");

        LightDiContext context = LightDi.initContextByClass(MergeBeanListConfiguration.class);

        // WHEN
        List<BeanClass> beans = context.getListOfBeans(BeanClass.class);

        assertThatBeansHaveExpectedValue(beans, expectedSet);
    }

    @Test
    public void testBeanListWithGetBean() {
        // GIVEN
        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("b");
        expectedSet.add("c");

        LightDiContext context = LightDi.initContextByClass(MergeBeanListConfiguration.class);

        // WHEN
        List<BeanClass> beans = (List) context.getBean("beans");

        // THEN
        assertThatBeansHaveExpectedValue(beans, expectedSet);
    }

    @Test
    public void testBeanWithInjectedBeanList() {
        // GIVEN
        Set<String> expectedSet = new HashSet<>();
        expectedSet.add("a");
        expectedSet.add("b");
        expectedSet.add("c");
        expectedSet.add("d");
        expectedSet.add("e");

        LightDiContext context = LightDi.initContextByClass(MergeBeanListConfiguration.class);

        // WHEN
        BeanListInjected injected = context.getBean(BeanListInjected.class);

        // THEN
        assertThatBeansHaveExpectedValue(injected.beans, expectedSet);
    }

    private void assertThatBeansHaveExpectedValue(List<BeanClass> beans, Set<String> expectedSet) {
        Set<String> values = beans.stream()
                .map(bean -> bean.data)
                .collect(Collectors.toSet());
        assertThat(values, is(expectedSet));
    }

}
