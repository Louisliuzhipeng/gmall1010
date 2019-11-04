package com.chirping.gmall.config;

import com.chirping.gmall.interceptors.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author 刘志鹏
 * @date 2019/10/31
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/error", "/bootstrap/**", "/css/**", "/image/**", "/img/**", "/js/**", "/scss/**", "/index/css/**", "/index/img/**", "/index/js/**", "/index/json/**", "/list/css/**", "/list/js/**", "/list/img/**", "/list/font/**", "/list/image/**");
    }
}