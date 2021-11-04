package com.boot.netty.config;

import com.boot.netty.websocket.WebSocketServer;
import com.boot.netty.websocket.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 设置排除路径，spring boot 2.*，注意排除掉静态资源的路径，不然静态资源无法访问
     */
    private final String[] excludePath = {
            "/static",
            // swagger资源
            "/webjars/**", "/swagger-resources/**", "/swagger-ui/**", "/v2/**","/doc.html",
            "/image/**"};

    @Resource(name = "nettyServerInitializer")
    private NettyServerInitializer nettyServerInitializer;

    @Bean
    public WebSocketServer getWebSocketServer() {
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.setPort(BootConfig.getNettyPort());
        webSocketServer.setChildChannelHandler(nettyServerInitializer);
        return webSocketServer;
    }

    @Bean("bossGroup")
    public EventLoopGroup getBossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean("workerGroup")
    public EventLoopGroup getWorkerGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public ServerBootstrap getServerBootstrap(){
        return new ServerBootstrap();
    }

    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
