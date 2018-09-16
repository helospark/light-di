package com.helospark.lightdi.it.plugintest.crudrepositorycontext;

import com.helospark.lightdi.it.plugintest.crudrepository.CrudRepository;

@CrudRepository
public interface MyCoolDao {

    public String getSomeData();
}
