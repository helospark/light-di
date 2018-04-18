package com.helospark.lightdi.it;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.PropertySource;
import com.helospark.lightdi.annotation.Value;

public class PropertySourceOnFileSystemIT {
    private static final String TEMP_PROPERTY_FILE_LOCATION = System.getProperty("java.io.tmpdir") + "/lightdi_test.properties";

    @Before
    public void setUp() throws IOException {
        File file = new File(TEMP_PROPERTY_FILE_LOCATION);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        fos.write("TMP_PROPERTY=test_value".getBytes(UTF_8));
        fos.close();
    }

    @After
    public void tearDown() {
        File file = new File(TEMP_PROPERTY_FILE_LOCATION);
        file.delete();
    }

    @Test
    public void testFilePropertySource() {
        // GIVEN
        LightDiContext context = LightDi.initContextByClass(ContextConfiguration.class);

        // WHEN
        ContextConfiguration result = context.getBean(ContextConfiguration.class);

        // THEN
        assertThat(result.getProperty(), is("test_value"));
    }

    @Configuration
    @PropertySource(value = "file:${java.io.tmpdir}/lightdi_test.properties")
    static class ContextConfiguration {
        @Value("${TMP_PROPERTY}")
        private String property;

        public String getProperty() {
            return property;
        }

    }

}
