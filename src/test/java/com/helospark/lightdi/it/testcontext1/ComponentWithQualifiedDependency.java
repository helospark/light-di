package com.helospark.lightdi.it.testcontext1;

import com.helospark.lightdi.annotation.Component;
import com.helospark.lightdi.annotation.Qualifier;

@Component
public class ComponentWithQualifiedDependency {
    private QualifiedBean qualifiedBean;

    public ComponentWithQualifiedDependency(@Qualifier("myQualifiedBean") QualifiedBean qualifiedBean) {
        this.qualifiedBean = qualifiedBean;
    }

    public QualifiedBean getQualifiedBean() {
        return qualifiedBean;
    }

}
