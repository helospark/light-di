package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Component
public class RequiredFalseFieldDependency {

    @Autowired(required = false)
    private NotABean notABean;

    public NotABean getNotABean() {
        return notABean;
    }

}
