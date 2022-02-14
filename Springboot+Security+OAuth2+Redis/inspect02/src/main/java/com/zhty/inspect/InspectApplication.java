package com.zhty.inspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@EnableConfigurationProperties
@SpringBootApplication
public class InspectApplication {

  public static void main(String[] args) {
    SpringApplication.run(InspectApplication.class, args);
  }

}
