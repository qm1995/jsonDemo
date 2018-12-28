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

    /**
     * 字段名，不填则默认参数名
     * @return
     */
    String fieldName() default "";

    /**
     * 默认值，不填则默认为null。注，如果不填且为基础类型，则会抛出异常
     * @return
     */
    String defaultValue() default "";
}
