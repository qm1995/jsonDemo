package com.qm.jsondemo.demo.util;

import jdk.nashorn.internal.ir.IfNode;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;

/** 
 * 类型转换工具类
 * @author qiumin
 * @create 2018/12/26 20:26
 * @desc
 **/
public class ConverterUtil {
    
    public static Converter getConverter(Class<?> clazz){
        if (isNumberType(clazz)){
            return new NumberConverter();
        }
        return null;
    }
    
    public static boolean isNumberType(Class<?> tClass){
        if (tClass == null){
            return false;
        }
        if (tClass == boolean.class || tClass == char.class){
            return false;
        }
        if (tClass.isPrimitive()){
            return true;
        }
        return tClass.getGenericSuperclass() == Number.class;
    }

    public static boolean isBooleanType(Class<?> tClass){
        if (tClass == boolean.class || tClass == Boolean.class){
            return true;
        }
        return false;
    }

    public static boolean isStringType(Class<?> tClass){
        if (tClass == char.class || tClass == String.class || tClass == Character.class){
            return true;
        }
        return false;
    }

}
