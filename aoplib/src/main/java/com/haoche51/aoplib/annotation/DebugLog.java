package com.haoche51.aoplib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author HaiboXu on 2018/11/7
 * @github https://github.com/JerryHsu1986
 */
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DebugLog {
}
