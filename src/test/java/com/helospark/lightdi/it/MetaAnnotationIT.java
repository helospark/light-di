package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.metaannotationcontext.AnnotationImportedConfiguration;
import com.helospark.lightdi.it.metaannotationcontext.MetaAnnotationConfiguration;
import com.helospark.lightdi.it.metaannotationcontext.toscan.BeanShouldBeComponentScanned;

public class MetaAnnotationIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = LightDi.initContextByClass(MetaAnnotationConfiguration.class);
    }

    @Test
    public void testShouldParseConfiguration() {
        // GIVEN

        // WHEN
        MetaAnnotationConfiguration result = context.getBean(MetaAnnotationConfiguration.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testShouldParseImport() {
        // GIVEN

        // WHEN
        AnnotationImportedConfiguration result = context.getBean(AnnotationImportedConfiguration.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testShouldParseComponentScan() {
        // GIVEN

        // WHEN
        BeanShouldBeComponentScanned result = context.getBean(BeanShouldBeComponentScanned.class);

        // THEN
        assertNotNull(result);
    }

    @Test
    public void testShouldParsePropertySource() {
        // GIVEN

        // WHEN
        BeanShouldBeComponentScanned result = context.getBean(BeanShouldBeComponentScanned.class);

        // THEN
        assertThat(result.getValue(), is("asd"));
    }
}
