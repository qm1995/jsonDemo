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
            /**
             * 只是为了防止一次请求中调用getReader(),getInputStream(),getParameter(),报错
             * 都清楚inputStream 并不具有重用功能，即多次读取同一个inputStream流，
             * 只有第一次读取时才有数据，后面再次读取inputStream 没有数据，
             * 即，getReader()，只能调用一次，但getParameter()可以调用多次，详情可见ContentCachingRequestWrapper源码
              */
            requestWrapper = new ContentCachingRequestWrapper(request);
        }
        chain.doFilter(requestWrapper == null ? req : requestWrapper, response);
    }
}
