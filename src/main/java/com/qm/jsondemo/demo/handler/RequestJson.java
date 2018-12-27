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
     * 默认值，不填则默认为null。注，如果为基础类型，则会抛出异常
     * @return
     */
    String defaultValue() default "";

    /**
     * 适用于集合中的泛型，如List<T>
     * 若为map结构，则key为string,value为T
     * 如果不指定，则统一为string类型，
     * -------------------------------
     * 本想由代码自动分析，但由于泛型擦除，故添加了这个，
     * 像google的typeToken  需静态指定，不能动态获取，所以行不通
     * @return
     */
    Class<?> elementType() default String.class;
}
