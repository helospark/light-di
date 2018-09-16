package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.LightDiContextConfiguration;
import com.helospark.lightdi.it.plugintest.crudrepository.CrudRepositoryPlugin;
import com.helospark.lightdi.it.plugintest.crudrepositorycontext.CrudRepositoryContextTest;
import com.helospark.lightdi.it.plugintest.crudrepositorycontext.MyCoolDao;
import com.helospark.lightdi.it.plugintest.sillystring.SillyStringPlugin;
import com.helospark.lightdi.it.plugintest.sillystringcontext.SillyStringTestConfiguration;

public class PluginIT {

    @Test
    public void testPluginConfigurationWithCrudRepository() {
        // GIVEN
        LightDiContextConfiguration config = LightDiContextConfiguration.builder()
                .withAdditionalDependencies(Collections.singletonList(new CrudRepositoryPlugin()))
                .build();

        // WHEN
        LightDiContext context = LightDi.initContextByPackage(CrudRepositoryContextTest.class.getPackage().getName(),
                config);

        // THEN
        MyCoolDao myCoolDao = context.getBean(MyCoolDao.class);
        assertThat(myCoolDao.getSomeData(), is("Database access result"));
    }

    @Test
    public void testPluginConfigurationWithSillyString() {
        // GIVEN
        LightDiContextConfiguration config = LightDiContextConfiguration.builder()
                .withAdditionalDependencies(Collections.singletonList(new SillyStringPlugin()))
                .build();

        // WHEN
        LightDiContext context = LightDi.initContextByClass(SillyStringTestConfiguration.class, config);

        // THEN
        SillyStringTestConfiguration sillyString = context.getBean(SillyStringTestConfiguration.class);
        assertThat(sillyString.getProperty(), is("Silly string :)"));
    }
}
