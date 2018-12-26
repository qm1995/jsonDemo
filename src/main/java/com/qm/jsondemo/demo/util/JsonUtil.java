package com.qm.jsondemo.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * @author qiumin
 * @create 2018/12/24 18:33
 * @desc
 **/
public class JsonUtil {


    public static String convertBeanToStr(Object bean){
        Gson gson = new Gson();
        return gson.toJson(bean);
    }


    public static <T> T convertStrToBean(Class<T> clazz,String s){
        if (s == null || "".equals(s)){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(s,((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
