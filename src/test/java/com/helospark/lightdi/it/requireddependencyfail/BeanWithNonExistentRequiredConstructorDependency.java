package com.helospark.lightdi.it.requireddependencyfail;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Component
public class BeanWithNonExistentRequiredConstructorDependency {
    private NotABean notABean;

    @Autowired
    public BeanWithNonExistentRequiredConstructorDependency(NotABean notABean) {
        super();
        this.notABean = notABean;
    }

    public NotABean getNotABean() {
        return notABean;
    }

}
