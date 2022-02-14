package com.zhty.inspect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-15 17:35
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  // 跨域配置
  @Override
  protected void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET","HEAD","POST","DELETE","OPTIONS")
        .allowedHeaders("*").exposedHeaders("access-control-allow-headers","access-control-allow-methods"
        ,"access-control-allow-origin","access-control-max-age","X-Frame-Options").allowCredentials(false)
        .maxAge(3600);
    super.addCorsMappings(registry);
  }


}
