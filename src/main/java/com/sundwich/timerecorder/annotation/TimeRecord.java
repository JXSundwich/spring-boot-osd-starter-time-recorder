package com.chehejia.timerecorder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sundwich
 * @date 2025/08/12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeRecord {
    /**
     * if record phase time, default true
     * @return boolean
     */
    boolean enablePhase() default true;

    /**
     * self define method name (for log output)
     * @return {@link String }
     */
    String name() default "";

}
