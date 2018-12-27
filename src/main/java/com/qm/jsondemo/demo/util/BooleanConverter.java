package com.qm.jsondemo.demo.util;


/**
 * 布尔类型
 * @author: qiumin
 * @create: 2018-12-27 10:29
 **/
public class BooleanConverter implements Converter{

    @Override
    public Object convert(Class<?> clazz, Object value) {
        if (value == null){
            return null;
        }
        if ((value instanceof String) && "".equals(String.valueOf(value))){
            return null;
        }
        if (clazz == boolean.class || clazz == Boolean.class){
            return Boolean.valueOf(String.valueOf(value));
        }
        throw new ClassCastException(clazz.getTypeName() + "can not cast Boolean type!");
    }
}
