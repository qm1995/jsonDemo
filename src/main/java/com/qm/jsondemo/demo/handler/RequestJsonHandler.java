package com.qm.jsondemo.demo.handler;

import com.qm.jsondemo.demo.util.Constant;
import com.qm.jsondemo.demo.util.ConverterUtil;
import com.qm.jsondemo.demo.util.JsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
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
        return methodParameter.hasMethodAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        Map<String,String> map = (Map<String, String>) request.getAttribute(Constant.REQUEST_BODY_DATA_NAME);
        if (map == null || map.size() == 0){
            return null;
        }
        RequestJson requestJson = methodParameter.getMethodAnnotation(RequestJson.class);
        String fieldName = requestJson.fieldName();
        if ("".equals(fieldName)){
            fieldName = methodParameter.getParameterName();
        }
        String orDefault = map.getOrDefault(fieldName, requestJson.defaultValue());
        Class<?> parameterType = methodParameter.getParameterType();
        return ConverterUtil.getConverter(parameterType).convert(parameterType,orDefault);
    }
}
