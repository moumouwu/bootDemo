package com.zhty.inspect.config.oauth2;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.web.DefaultSecurityFilterChain;

/**
 * 对于白名单请求头中 Authorization=bearer39b3a178-d330-46a6-a845-fd89607b28e6格式的字段
 * 应该去除该字段，忽略校验
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 16:45
 */
@Configuration("permitAllNoAuthConfig")
public class PermitAllNoAuthConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  @Autowired
  private Filter permitAuthenticationFilter;

  @Override
  public void configure(HttpSecurity builder) throws Exception {
    builder.addFilterBefore(permitAuthenticationFilter, OAuth2AuthenticationProcessingFilter.class);
  }
}
