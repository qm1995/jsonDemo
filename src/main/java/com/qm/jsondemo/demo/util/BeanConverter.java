package com.qm.jsondemo.demo.util;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * 普通javabean
 * @author: qiumin
 * @create: 2018-12-27 10:31
 **/
public class BeanConverter implements Converter{

    /**
     * 只支持bean json 转 javabean对象，即value为json字符串
     * @param clazz
     * @param value
     * @return
     */
    @Override
    public Object convert(Type clazz, Object value) {
        if (value == null){
            return null;
        }
        if ((value instanceof String) && "".equals(String.valueOf(String.valueOf(value)))){
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(String.valueOf(value), clazz);
        }catch (Exception e){
            throw new ClassCastException(clazz.getTypeName() + " can not cast Bean type!");
        }
    }
}
