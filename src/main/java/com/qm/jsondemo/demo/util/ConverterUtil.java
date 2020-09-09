package com.qm.jsondemo.demo.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.BreakIterator;
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
        DEFAULT_REGISTER_CONVERTER_MAP.put(Boolean.class, new BooleanConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(String.class, new StringConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Object.class, new BeanConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Collection.class, new CollectionConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Map.class, new MapConverter());
        DEFAULT_REGISTER_CONVERTER_MAP.put(Date.class, new DateConverter());
    }

    /**
     * 根据clazz获取合适的转换器
     *
     * @param clazz
     * @return
     */
    public static Converter getConverter(Class<?> clazz) {
        for (Map.Entry<Class<?>, Converter> convert : DEFAULT_REGISTER_CONVERTER_MAP.entrySet()) {
            Converter value = convert.getValue();
            if (value.support(clazz)) {
                return value;
            }
        }
        throw new RuntimeException("unSupport the " + clazz.getName() + " type converter");
    }

    /**
     * 是否为数值类型
     *
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
     *
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
     *
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
     *
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
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), tClass);
                if (descriptor.getWriteMethod() != null && descriptor.getReadMethod() != null) {
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
     *
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
     *
     * @param tClass
     * @return
     */
    public static boolean isDateType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
        return Date.class.isAssignableFrom(tClass);
    }

    /**
     * 添加转换器，
     * @param clazz
     * @param converter
     * @param isCover 是否覆盖已存在的
     * @return
     */
    public static boolean addConverter(Class<?> clazz,Converter converter,boolean isCover){
        if (!isCover && DEFAULT_REGISTER_CONVERTER_MAP.containsKey(clazz)){
            return false;
        }
        DEFAULT_REGISTER_CONVERTER_MAP.put(clazz,converter);
        return true;
    }

    /**
     * 以不覆盖的形式添加转换器
     * @param clazz
     * @param converter
     * @return
     */
    public static boolean addConverter(Class<?> clazz,Converter converter){
        return addConverter(clazz, converter,false);
    }
}
