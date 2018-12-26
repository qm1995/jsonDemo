package com.qm.jsondemo.demo;

import com.qm.jsondemo.demo.filter.RequestJsonFilter;
import com.qm.jsondemo.demo.handler.RequestJsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author qiumin
 * @create 2018/12/25 18:20
 * @desc
 **/
@Configuration
@EnableWebMvc
public class WebConfigure implements WebMvcConfigurer {


    @Autowired
    private RequestJsonHandler requestJsonHandler;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requestJsonHandler);
    }

    @Bean
    public FilterRegistrationBean filterRegister() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RequestJsonFilter());
        //拦截路径
        registration.addUrlPatterns("/");
        //过滤器名称
        registration.setName("requestJsonFilter");
        //是否自动注册 false 取消Filter的自动注册
        registration.setEnabled(false);
        //过滤器顺序,需排在第一位
        registration.setOrder(1);
        return registration;
    }
}
