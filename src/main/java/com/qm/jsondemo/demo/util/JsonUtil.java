package com.qm.jsondemo.demo.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

/**
 * @author qiumin
 **/
public class JsonUtil {


    public static String convertBeanToStr(Object bean){
        Gson gson = new Gson();
        return gson.toJson(bean);
    }


    /**
     * json str和java类直接的转换
     * @param clazz
     * @param s
     * @param <T>
     * @return
     */
    public static <T> T convertStrToBean(Class<T> clazz,String s){
        if (s == null || "".equals(s)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(s,((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
