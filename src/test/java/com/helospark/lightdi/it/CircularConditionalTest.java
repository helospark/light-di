package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.descriptor.DependencyDescriptorQuery;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnBeanDependency;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnBeanDependency.ConditionalOnBeanFirstBean;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnBeanDependency.ConditionalOnBeanSecondBean;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnMissingBeanDependency;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnMissingBeanDependency.ConditionalOnMissingBeanFirstBean;
import com.helospark.lightdi.it.conditionaltest.CircularConditionalOnMissingBeanDependency.ConditionalOnMissingBeanSecondBean;

public class CircularConditionalTest {

    @Test
    public void testOnBeanConditional() {
        // GIVEN

        // WHEN
        LightDiContext result = LightDi.initContextByClass(CircularConditionalOnBeanDependency.class);

        // THEN

        assertThat(result.getBean(optionalDependency(ConditionalOnBeanFirstBean.class)), is(not(nullValue())));
        assertThat(result.getBean(optionalDependency(ConditionalOnBeanSecondBean.class)), is(not(nullValue())));
    }

    @Test
    public void testOnMissingBeanConditional() {
        // GIVEN

        // WHEN
        LightDiContext result = LightDi.initContextByClass(CircularConditionalOnMissingBeanDependency.class);

        // THEN

        assertThat(result.getBean(optionalDependency(ConditionalOnMissingBeanFirstBean.class)), is(nullValue()));
        assertThat(result.getBean(optionalDependency(ConditionalOnMissingBeanSecondBean.class)), is(nullValue()));
    }

    private DependencyDescriptorQuery optionalDependency(Class<?> clazz) {
        return DependencyDescriptorQuery.builder()
                .withClazz(clazz)
                .withRequired(false)
                .build();
    }

}
