package com.qm.jsondemo.demo.util;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * map转换器
 * @author: qiumin
 * @create: 2018-12-27 11:19
 **/
@Deprecated
public class MapConverter implements Converter{


    /**
     * 只支持json转map
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
        }else if (value instanceof String && "".equals(String.valueOf(value))){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(value),clazz);
    }
}
