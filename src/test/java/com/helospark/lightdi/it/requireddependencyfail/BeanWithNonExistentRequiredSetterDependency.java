package com.helospark.lightdi.it.requireddependencyfail;

import com.helospark.lightdi.annotation.Autowired;
import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.it.conditionaltest.NotABean;

@Component
public class BeanWithNonExistentRequiredSetterDependency {
    private NotABean notABean;

    @Autowired
    public void setNotABean(NotABean notABean) {
        this.notABean = notABean;
    }

    public NotABean getNotABean() {
        return notABean;
    }

}
