package com.helospark.lightdi.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotated bean will take precedence when multiple beans match.
 * <p>
 * Useful for overriding beans during integration testing and overriding beans provided by librarires.
 * <br>
 * Note that there could be multiple primary beans, in which case the first one is used by {com.helospark.lightdi.annotation.Order Order}.
 * @author helospark
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Primary {

}
