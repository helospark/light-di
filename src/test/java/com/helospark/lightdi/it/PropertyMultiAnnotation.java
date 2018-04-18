package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.multipropertyannotationcontext.ConfigurationWithRepeatedAnnotation;
import com.helospark.lightdi.it.multipropertyannotationcontext.PropertySourcesAnnotationConfiguration;

public class PropertyMultiAnnotation {

    @Test
    public void testRepeatedAnnotation() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(ConfigurationWithRepeatedAnnotation.class);

        // WHEN
        ConfigurationWithRepeatedAnnotation bean = context.getBean(ConfigurationWithRepeatedAnnotation.class);

        // THEN
        assertThat(bean.getFirstProperty(), is("first"));
        assertThat(bean.getSecondProperty(), is("second"));
    }

    @Test
    public void testPropertySourcesAnnotation() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(PropertySourcesAnnotationConfiguration.class);

        // WHEN
        PropertySourcesAnnotationConfiguration bean = context.getBean(PropertySourcesAnnotationConfiguration.class);

        // THEN
        assertThat(bean.getFirstProperty(), is("first"));
        assertThat(bean.getSecondProperty(), is("second"));
    }

}
