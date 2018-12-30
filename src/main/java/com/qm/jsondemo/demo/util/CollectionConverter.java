package com.qm.jsondemo.demo.util;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author: qiumin
 **/
public class CollectionConverter implements Converter{

    /**
     * 只支持json str 转 集合类型
     * @param clazz
     * @param value
     * @return
     */
    @Override
    public Object convert(Type clazz, Object value) {
        if (clazz == null){
            throw new RuntimeException("type must be not null");
        }else if (value == null){
            return null;
        }else if (value instanceof String && "".equals(String.valueOf(String.valueOf(value)))){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(value),clazz);
    }
}
