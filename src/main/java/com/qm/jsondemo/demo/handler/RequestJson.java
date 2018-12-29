package com.qm.jsondemo.demo.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注意点，此注解不能和@RequestBody注解共用，即在一个方法内的参数
 * 不能同时出现这两个注解，如
 * error:
 *     test1(@RequestJson Integer age,@RequestBody String name), 这是错误的，
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
     * 默认值，不填则默认为null。
     * @return
     */
    String defaultValue() default "";
}
