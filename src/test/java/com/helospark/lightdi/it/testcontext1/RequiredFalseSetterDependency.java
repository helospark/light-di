package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Component
public class RequiredFalseSetterDependency {
    private NotABean notABean;

    @Autowired(required = false)
    public void setNotABean(NotABean notABean) {
        this.notABean = notABean;
    }

    public NotABean getNotABean() {
        return notABean;
    }

}
