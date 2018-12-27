package com.qm.jsondemo.demo.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

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

    public static void main(String[] args){
        String s = "{\"sid\":1,\"stuName\":\"里斯\"}";
        
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(s);
        if (jsonElement.isJsonArray()){
            List list = gson.fromJson(jsonElement, List.class);
            System.out.println(list);
        }else {
            Map map = gson.fromJson(jsonElement, Map.class);
            System.out.println(map.get("sid")+":"+map.get("stuName"));
        }
    }
}
