package com.example.demo2.conf;

import com.example.demo2.intercept.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();
        //创建白名单
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/css/**");
        excludePath.add("/js/**");
        excludePath.add("/bootstrap3/**");
        excludePath.add("/images/**");
        excludePath.add("/web/register.html");
        excludePath.add("/web/login.html");
        excludePath.add("/users/reg");
        excludePath.add("/users/login");
        excludePath.add("/districts/");
        //注册拦截器
        registry.addInterceptor(interceptor).excludePathPatterns(excludePath).addPathPatterns("/**");
    }

}
