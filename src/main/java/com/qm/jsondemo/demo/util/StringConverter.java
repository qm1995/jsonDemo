package com.qm.jsondemo.demo.util;


import java.lang.reflect.Type;

/**
 * String 类型转换器
 * @author: qiumin
 **/
public class StringConverter implements Converter{
    @Override
    public Object convert(Type type, Object value) {
        Class<?> clazz = (Class<?>) type;
        if (clazz == String.class || clazz == char.class || clazz == Character.class){
            return String.valueOf(value);
        }
        throw new ClassCastException(clazz.getTypeName() + "can not cast String type!");
    }

    @Override
    public boolean support(Class<?> clazz) {
        return ConverterUtil.isStringType(clazz);
    }
}
