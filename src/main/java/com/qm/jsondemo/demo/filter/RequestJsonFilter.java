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
            requestWrapper = new ContentCachingRequestWrapper(request);
        }
        chain.doFilter(requestWrapper == null ? req : requestWrapper, response);
    }
}
