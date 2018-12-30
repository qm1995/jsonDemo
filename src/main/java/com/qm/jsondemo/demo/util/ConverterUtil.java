package com.qm.jsondemo.demo.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类型转换工具类
 *
 * @author qiumin
 * @create 2018/12/26 20:26
 * @desc
 **/
public class ConverterUtil {

    private static final Map<Class<?>, Converter> DEFAULT_REGISTER_CONVERTER_MAP = new ConcurrentHashMap<>();

    static {
        DEFAULT_REGISTER_CONVERTER_MAP.put(Number.class, new NumberConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Boolean.class, new NumberConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(String.class, new StringConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Object.class, new BeanConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Collection.class, new CollectionConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Map.class, new MapConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Date.class, new DateConverter());
    }

    /**
     * 根据clazz获取合适的转换器
     * @param clazz
     * @return
     */
    public static Converter getConverter(Class<?> clazz) {
        Converter converter = null;
        if (isNumberType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Number.class);
        } else if (isBooleanType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Boolean.class);
        } else if (isBeanType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Object.class);
        } else if (isCollectionType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Collection.class);
        } else if (isStringType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(String.class);
        } else if (isMapType(clazz)) {
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Map.class);
        } else if (isDateType(clazz)){
            converter = DEFAULT_REGISTER_CONVERTER_MAP.get(Date.class);
        }
        if (converter == null) {
            throw new RuntimeException("unSupport the " + clazz.getName() + " type converter");
        }
        return converter;
    }

    /**
     * 是否为数值类型
     * @param tClass
     * @return
     */
    public static boolean isNumberType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
        if (tClass == boolean.class || tClass == char.class) {
            return false;
        }
        if (tClass.isPrimitive()) {
            return true;
        }
        return Number.class.isAssignableFrom(tClass);
    }

    /**
     * 是否为boolean类型
     * @param tClass
     * @return
     */
    public static boolean isBooleanType(Class<?> tClass) {
        if (tClass == boolean.class || tClass == Boolean.class) {
            return true;
        }
        return false;
    }

    /**
     * 是否为string 类型
     * @param tClass
     * @return
     */
    public static boolean isStringType(Class<?> tClass) {
        if (tClass == char.class || tClass == String.class || tClass == Character.class) {
            return true;
        }
        return false;
    }

    /**
     * 是否为集合类型
     * @param tClass
     * @return
     */
    public static boolean isCollectionType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
        return Collection.class.isAssignableFrom(tClass);
    }

    /**
     * 简单的判断 是否为普通javabean ，即该类中字段是否含有 getter/setter方法
     *
     * @param tClass
     * @return
     */
    public static boolean isBeanType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
        if (tClass.isArray() || tClass.isInterface() ||
                Modifier.isAbstract(tClass.getModifiers()) || tClass.isEnum() ||
                tClass.isAnnotation() || tClass.isPrimitive() || Date.class.isAssignableFrom(tClass)) {
            return false;
        }
        Field[] declaredFields = tClass.getDeclaredFields();
        for (Field field : declaredFields) {
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(),tClass);
                if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null){
                    return true;
                }
            } catch (IntrospectionException e) {
                // ignore
            }
        }
        return false;
    }

    /**
     * 是否为Map接口的实现类/子接口
     * @param tClass
     * @return
     */
    public static boolean isMapType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
       return Map.class.isAssignableFrom(tClass);
    }


    /**
     * 是否为日期类型
     * @param tClass
     * @return
     */
    public static boolean isDateType(Class<?> tClass){
        if (tClass == null){
            return false;
        }
        return Date.class.isAssignableFrom(tClass);
    }
}
