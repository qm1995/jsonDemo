package com.qm.jsondemo.demo.util;


import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

/**
 * @author: qiumin
 * @create: 2018-12-27 10:36
 **/
public class CollectionConverter implements Converter{

    /**
     * 只支持json str 转 集合类型
     * @param clazz
     * @param value
     * @return
     */
    @Override
    public Object convert(Class<?> clazz, Object value) {
        if (clazz == null){
            throw new RuntimeException("type must not null");
        }else if (value == null){
            return null;
        }else if (value instanceof String && "".equals(value)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(value),((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
