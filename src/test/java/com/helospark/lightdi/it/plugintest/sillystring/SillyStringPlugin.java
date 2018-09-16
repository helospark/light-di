package com.helospark.lightdi.it.plugintest.sillystring;

import com.helospark.lightdi.internal.InternalDi;
import com.helospark.lightdi.internal.LightDiPlugin;

/**
 * An example plugin that replaces all injected string values with a silly string by replacing the bean responsible for resolving that value.
 * @author helospark
 */
public class SillyStringPlugin implements LightDiPlugin {

    @Override
    public InternalDi customizeInteralDi(InternalDi original) {
        return new SillyStringDi(original);
    }
}
