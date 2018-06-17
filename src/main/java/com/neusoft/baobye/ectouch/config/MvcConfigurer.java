package com.neusoft.baobye.ectouch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {


    //uri 到视图的映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //注册
        registry.addViewController("/register").setViewName("register");
    }
}
