package com.zhty.inspect.config.oauth2.filter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 15:44
 */
public class CustomOauth2ClientCredentialsTokenEndpointFilter extends
    ClientCredentialsTokenEndpointFilter {

  private AuthorizationServerSecurityConfigurer configurer;
  private AuthenticationEntryPoint authenticationEntryPoint;


  public CustomOauth2ClientCredentialsTokenEndpointFilter(AuthorizationServerSecurityConfigurer configurer) {
    this.configurer = configurer;
  }

  @Override
  public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
    super.setAuthenticationEntryPoint(null);
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Override
  protected AuthenticationManager getAuthenticationManager() {
    return configurer.and().getSharedObject(AuthenticationManager.class);
  }

  @Override
  public void afterPropertiesSet() {
    setAuthenticationFailureHandler((request, response, e) -> {
      System.out.println(e.getClass().getSimpleName());
      authenticationEntryPoint.commence(request, response, e);
    });
    setAuthenticationSuccessHandler((request, response, authentication) -> {
    });
  }
}
