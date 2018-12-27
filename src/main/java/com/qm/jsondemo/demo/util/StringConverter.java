package com.qm.jsondemo.demo.util;


/**
 * String 类型转换器
 * @author: qiumin
 * @create: 2018-12-27 09:22
 **/
public class StringConverter implements Converter{
    @Override
    public Object convert(Class<?> clazz, Object value) {
        if (clazz == String.class || clazz == char.class || clazz == Character.class){
            return String.valueOf(value);
        }
        throw new ClassCastException(clazz.getTypeName() + "can not cast String type!");
    }
}
