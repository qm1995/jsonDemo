package com.qm.jsondemo.demo.util;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数值类型转换器
 * @author qiumin
 * @desc
 **/
public class NumberConverter implements Converter{

    @Override
    public Object convert(Type type, Object value){
        Class<?> clazz = null;
        if (!(type instanceof Class)){
            return null;
        }
        clazz = (Class<?>) type;
        if (clazz == null){
            throw new RuntimeException("类型不能为空");
        }else if (value == null){
            return null;
        }else if (value instanceof String && "".equals(String.valueOf(value))){
            return null;
        }else if (!clazz.isPrimitive() && clazz.getGenericSuperclass() != Number.class){
            throw new ClassCastException(clazz.getTypeName() + "can not cast Number type!");
        }
        if (clazz == int.class || clazz == Integer.class){
            return Integer.valueOf(String.valueOf(value));
        }else if (clazz == short.class || clazz == Short.class){
            return Short.valueOf(String.valueOf(value));
        }else if (clazz == byte.class || clazz == Byte.class){
            return Byte.valueOf(String.valueOf(value));
        }else if (clazz == float.class || clazz == Float.class){
            return Float.valueOf(String.valueOf(value));
        }else if (clazz == double.class || clazz == Double.class){
            return Double.valueOf(String.valueOf(value));
        }else if (clazz == long.class || clazz == Long.class){
            return Long.valueOf(String.valueOf(value));
        }else if (clazz == BigDecimal.class){
            return new BigDecimal(String.valueOf(value));
        }else if (clazz == BigInteger.class){
            return new BigDecimal(String.valueOf(value));
        }else {
            throw new RuntimeException("This type conversion is not supported!");
        }
    }

    @Override
    public boolean support(Class<?> clazz) {
        return ConverterUtil.isNumberType(clazz);
    }


}
