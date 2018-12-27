package com.qm.jsondemo.demo.filter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.qm.jsondemo.demo.util.Constant;
import com.qm.jsondemo.demo.util.JsonUtil;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 过滤器
 * @author qiumin
 * @create 2018/12/24 18:30
 * @desc
 **/
public class RequestJsonFilter implements Filter {


    /**
     * 用来对request中的Body数据进一步包装
     * @param req
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(req instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) req;
            resolveRequestBody(request);
            requestWrapper = new ContentCachingRequestWrapper(request);
        }
        chain.doFilter(requestWrapper == null ? req : requestWrapper, response);
    }

    private void resolveRequestBody(HttpServletRequest request){
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
