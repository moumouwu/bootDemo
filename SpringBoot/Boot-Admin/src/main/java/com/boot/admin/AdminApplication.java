package com.boot.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author binSin
 * @date 2021/10/14
 */
@EnableAdminServer // 开启 springboot admin 服务端
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) // 排除数据库
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
