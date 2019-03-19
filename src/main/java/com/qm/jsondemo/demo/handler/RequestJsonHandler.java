package com.qm.jsondemo.demo.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qm.jsondemo.demo.util.*;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * @author qiumin
 **/
@Component
public class RequestJsonHandler implements HandlerMethodArgumentResolver {

    /**
     * json类型
     */
    private static final String JSON_CONTENT_TYPE = "application/json";


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String contentType = request.getContentType();
        // 不是json
        if (!JSON_CONTENT_TYPE.equalsIgnoreCase(contentType)){
            return null;
        }
        Object obj =  request.getAttribute(Constant.REQUEST_BODY_DATA_NAME);
        synchronized (RequestJsonHandler.class) {
            if (obj == null) {
                resolveRequestBody(request);
                obj = request.getAttribute(Constant.REQUEST_BODY_DATA_NAME);
                if (obj == null) {
                    return null;
                }
            }
        }
        RequestJson requestJson = methodParameter.getParameterAnnotation(RequestJson.class);
        if (obj instanceof Map){
            Map<String, String> map = (Map<String, String>)obj;
            return dealWithMap(map,requestJson,methodParameter);
        }else if (obj instanceof List){
            List<Map<String,String>> list = (List<Map<String,String>>)obj;
            return dealWithArray(list,requestJson,methodParameter);
        }
        return null;
    }

    /**
     * 处理第一层json结构为数组结构的json串
     * 这种结构默认就认为 为类似List<JavaBean> 结构，转json即为List<Map<K,V>> 结构，
     * 其余情况不作处理，若controller层为第一种，则数组里的json，转为javabean结构，字段名要对应，
     * 注意这里defaultValue不起作用
     * @param list
     * @param requestJson
     * @param methodParameter
     * @return
     */
    private Object dealWithArray(List<Map<String,String>> list,RequestJson requestJson,MethodParameter methodParameter){
        Class<?> parameterType = methodParameter.getParameterType();
        return ConverterUtil.getConverter(parameterType).convert(methodParameter.getGenericParameterType(),JsonUtil.convertBeanToStr(list));
    }
    /**
     * 处理{"":""}第一层json结构为map结构的json串，
     * @param map
     * @param requestJson
     * @param methodParameter
     * @return
     */
    private Object dealWithMap(Map<String,String> map,RequestJson requestJson,MethodParameter methodParameter){
        String fieldName = requestJson.fieldName();
        if ("".equals(fieldName)){
            fieldName = methodParameter.getParameterName();
        }
        Class<?> parameterType = methodParameter.getParameterType();
        String orDefault = null;
        if (map.containsKey(fieldName)){
            orDefault = map.get(fieldName);
        }else if (ConverterUtil.isMapType(parameterType)){
            return map;
        }else if (ConverterUtil.isBeanType(parameterType) || ConverterUtil.isCollectionType(parameterType)){
            orDefault = JsonUtil.convertBeanToStr(map);
        }else {
            orDefault = map.getOrDefault(fieldName,requestJson.defaultValue());
        }
        Converter converter = ConverterUtil.getConverter(parameterType);
        if (converter instanceof DateConverter && !"".equals(requestJson.datePattern())){
            DateConverter dateConverter = (DateConverter) converter;
            dateConverter.setDatePattern(requestJson.datePattern());
        }
        return converter.convert(methodParameter.getGenericParameterType(),orDefault);
    }

    /**
     * 解析request中的body数据
     * @param request
     */
    private void resolveRequestBody(ServletRequest request){
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String parameterValues = sb.toString();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(parameterValues);
            if (element.isJsonArray()){
                List<Map<String,String>> list = new ArrayList<>();
                list = JsonUtil.convertStrToBean(list.getClass(),parameterValues);
                request.setAttribute(Constant.REQUEST_BODY_DATA_NAME, list);
            }else {
                Map<String, String> map = new HashMap<>();
                map = JsonUtil.convertStrToBean(map.getClass(), parameterValues);
                request.setAttribute(Constant.REQUEST_BODY_DATA_NAME, map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                    //e.printStackTrace();
                }
            }
        }
    }
}
