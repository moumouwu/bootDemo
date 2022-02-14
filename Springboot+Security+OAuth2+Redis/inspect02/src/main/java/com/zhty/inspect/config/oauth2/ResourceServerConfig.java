package com.zhty.inspect.config.oauth2;

import com.zhty.inspect.config.oauth2.handler.Oauth2AccessDeniedHandler;
import com.zhty.inspect.config.oauth2.handler.Oauth2AuthExceptionEntryPoint;
import com.zhty.inspect.config.oauth2.handler.Oauth2LogoutSuccessHandler;
import com.zhty.inspect.config.security.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-16 9:05
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  /**
   * 短信验证码获取接口:  /sms/get-code
   * 角色 USER:系统注册过的人， TEMPER:微信小程序端普通用户，系统中没有记录的人 对于角色: TEMPER 来说，只能访问 /t1/** 及开放接口的内容
   */
  public final static String[] WHITELIST = {"/sms/get-code"};

  /**
   * 默认资源ID
   */
  @Value("${custom.default-resource-id}")
  private String DEFAULT_RESOURCE_ID;

  /**
   * 退出登录路径
   */
  public final static String LOGOUT_URI = "/oauth/logout";

  @Autowired
  private Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;

  @Autowired
  private Oauth2AccessDeniedHandler accessDeniedHandler;

  @Autowired
  private PermitAllNoAuthConfig permitAllNoAuthConfig;

  @Autowired
  private Oauth2LogoutSuccessHandler logoutSuccessHandler;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(DEFAULT_RESOURCE_ID).accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(authExceptionEntryPoint);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    // 禁止httBasic()登录方式
    http.httpBasic().disable();
    http.authorizeRequests().antMatchers(WHITELIST).permitAll().and().apply(permitAllNoAuthConfig)
        .and().authorizeRequests().antMatchers("/oauth/**", LOGOUT_URI).permitAll()
        .antMatchers("/t1/**").hasAnyRole(Values.reg, Values.unreg)
        .anyRequest().hasRole(Values.reg);
    http.logout().logoutUrl(LOGOUT_URI).logoutSuccessHandler(logoutSuccessHandler);
  }

}
