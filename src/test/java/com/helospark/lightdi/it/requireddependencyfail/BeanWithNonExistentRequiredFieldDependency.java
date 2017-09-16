package com.helospark.lightdi.it.requireddependencyfail;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Component
public class BeanWithNonExistentRequiredFieldDependency {

    @Autowired
    private NotABean notABean;

    public NotABean getNotABean() {
        return notABean;
    }

}
