package com.test.restful.Controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registy){
        registy.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login").excludePathPatterns("/static/**").excludePathPatterns("/verifyImage").excludePathPatterns("/loginCheck");
    }
}
