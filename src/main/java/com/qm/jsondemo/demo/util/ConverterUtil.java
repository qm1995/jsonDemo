package com.qm.jsondemo.demo.util;

import jdk.nashorn.internal.ir.IfNode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
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
    }


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
        }
        if (converter == null) {
            throw new RuntimeException("unSupport the " + clazz.getName() + " type converter");
        }
        return converter;
    }

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

    public static boolean isBooleanType(Class<?> tClass) {
        if (tClass == boolean.class || tClass == Boolean.class) {
            return true;
        }
        return false;
    }

    public static boolean isStringType(Class<?> tClass) {
        if (tClass == char.class || tClass == String.class || tClass == Character.class) {
            return true;
        }
        return false;
    }

    public static boolean isCollectionType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
        return Collection.class.isAssignableFrom(tClass);
    }

    /**
     * 简单的判断 是否为普通javabean ，即是否还有 setter方法
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
                tClass.isAnnotation() || tClass.isPrimitive()) {
            return false;
        }
        Method[] methods = tClass.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (method.getParameterCount() == 1 && name.startsWith("set")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMapType(Class<?> tClass) {
        if (tClass == null) {
            return false;
        }
       return Map.class.isAssignableFrom(tClass);
    }
}
