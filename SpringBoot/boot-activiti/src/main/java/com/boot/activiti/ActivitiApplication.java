package com.boot.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author binSin
 * @date 2021/11/30
 */
@SpringBootApplication(
        exclude = {SecurityAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}
