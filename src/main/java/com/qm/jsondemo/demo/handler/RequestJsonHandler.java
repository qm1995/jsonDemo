package com.qm.jsondemo.demo.handler;

import com.google.gson.reflect.TypeToken;
import com.qm.jsondemo.demo.model.Student;
import com.qm.jsondemo.demo.util.Constant;
import com.qm.jsondemo.demo.util.ConverterUtil;
import com.qm.jsondemo.demo.util.JsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.rmi.CORBA.Stub;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author qiumin
 * @create 2018/12/24 18:26
 * @desc
 **/
@Component
public class RequestJsonHandler implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Object obj =  request.getAttribute(Constant.REQUEST_BODY_DATA_NAME);
        if (obj == null){
            return null;
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

        TypeToken<?> typeToken = TypeToken.getParameterized(parameterType, requestJson.elementType());
        return ConverterUtil.getConverter(parameterType).convert(typeToken.getType(),JsonUtil.convertBeanToStr(list));
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
        return ConverterUtil.getConverter(parameterType).convert(parameterType,orDefault);
    }
}
