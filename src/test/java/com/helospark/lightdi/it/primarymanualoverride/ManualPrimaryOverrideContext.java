package com.helospark.lightdi.it.primarymanualoverride;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.ComponentScan;
import com.helospark.lightdi.annotation.Configuration;

@Configuration
@ComponentScan
public class ManualPrimaryOverrideContext {
    @Autowired
    private RootBean rootBean;

    public RootBean getRootBean() {
        return rootBean;
    }

}
