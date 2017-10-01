package com.helospark.lightdi.it.invalidconfiguration;

import com.helospark.lightdi.annotation.Configuration;
import com.helospark.lightdi.annotation.Eager;
import com.helospark.lightdi.annotation.Lazy;

@Lazy
@Eager
@Configuration
public class BothLazyAndEagerInitialized {

}
