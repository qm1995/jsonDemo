package com.qm.jsondemo.demo.util;

import java.lang.reflect.Type;

/**
 * 类型转换接口
 * @author qiumin
 * @desc
 **/
public interface Converter {

    /**
     * 将value转为clazz类型
     * @param clazz
     * @param value
     * @return
     */
    Object convert(Type clazz, Object value);
}
