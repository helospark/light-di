package com.helospark.lightdi.it;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.helospark.lightdi.LightDi;
import com.helospark.lightdi.LightDiContext;
import com.helospark.lightdi.it.collectioninjecttest.ArrayListCollectionOnSetterBean;
import com.helospark.lightdi.it.collectioninjecttest.BeanToInjectCollection;
import com.helospark.lightdi.it.collectioninjecttest.BeanToInjectSet;
import com.helospark.lightdi.it.collectioninjecttest.CollectionOnFieldBean;
import com.helospark.lightdi.it.collectioninjecttest.CollectionOnSetterBean;
import com.helospark.lightdi.it.collectioninjecttest.Implementation1;
import com.helospark.lightdi.it.collectioninjecttest.Implementation2;

public class CollectionInjectIT {
    private LightDiContext context;

    @Before
    public void setUp() {
        LightDi lightDi = new LightDi();
        context = LightDi.initContextByPackage(BeanToInjectCollection.class.getPackage().getName());
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void testCollectionInject() {
        // GIVEN

        // WHEN
        BeanToInjectCollection bean = context.getBean(BeanToInjectCollection.class);

        // THEN
        assertThat(bean.getCommonInterface().size(), is(2));
    }

    @Test
    public void testCollectionInjectShouldContainExpectedElements() {
        // GIVEN
        Implementation1 impl1 = context.getBean(Implementation1.class);
        Implementation2 impl2 = context.getBean(Implementation2.class);

        // WHEN
        BeanToInjectCollection bean = context.getBean(BeanToInjectCollection.class);

        // THEN
        assertThat(bean.getCommonInterface(), containsInAnyOrder(impl1, impl2));
    }

    @Test
    public void testSetInject() {
        // GIVEN

        // WHEN
        BeanToInjectSet bean = context.getBean(BeanToInjectSet.class);

        // THEN
        assertThat(bean.getCommonInterface().size(), is(2));
    }

    @Test
    public void testCollectionInjectOnField() {
        // GIVEN

        // WHEN
        CollectionOnFieldBean bean = context.getBean(CollectionOnFieldBean.class);

        // THEN
        assertThat(bean.getCommonInterface().size(), is(2));
    }

    @Test
    public void testCollectionInjectOnSetter() {
        // GIVEN

        // WHEN
        CollectionOnSetterBean bean = context.getBean(CollectionOnSetterBean.class);

        // THEN
        assertThat(bean.getCommonInterface().size(), is(2));
    }

    @Test
    public void testArrayListInjectOnSetter() {
        // GIVEN

        // WHEN
        ArrayListCollectionOnSetterBean bean = context.getBean(ArrayListCollectionOnSetterBean.class);

        // THEN
        assertThat(bean.getCommonInterface().size(), is(2));
    }
}
