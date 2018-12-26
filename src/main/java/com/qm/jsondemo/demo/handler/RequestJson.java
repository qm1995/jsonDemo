package com.qm.jsondemo.demo.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qiumin
 * @create 2018/12/24 18:27
 * @desc
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestJson {

    String fieldName() default "";

    String defaultValue() default "";
}
