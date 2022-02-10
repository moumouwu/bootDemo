package com.pay.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 设置排除路径，spring boot 2.*，注意排除掉静态资源的路径，不然静态资源无法访问
     */
    private final String[] excludePath = {"/static"};



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+"F:/image/");
    }
}
