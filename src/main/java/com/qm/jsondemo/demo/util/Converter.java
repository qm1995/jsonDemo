package com.qm.jsondemo.demo.util;

/**
 * 类型转换接口
 * @author qiumin
 * @create 2018/12/26 20:08
 * @desc
 **/
public interface Converter {

    /**
     * 将value转为clazz类型
     * @param clazz
     * @param value
     * @return
     */
    Object convert(Class<?> clazz,Object value);
}
