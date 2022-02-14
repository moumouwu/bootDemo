package com.zhty.inspect.config.oauth2;

import com.zhty.inspect.config.exception.CustomSecurityWebResponseExceptionTranslator;
import com.zhty.inspect.config.oauth2.filter.CustomOauth2ClientCredentialsTokenEndpointFilter;
import com.zhty.inspect.config.oauth2.granter.CustomBaseTokenGranter;
import com.zhty.inspect.config.oauth2.handler.Oauth2AuthExceptionEntryPoint;
import com.zhty.inspect.config.security.SecurityUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-16 9:13
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private SecurityUserDetailsService userDetailsService;

  @Autowired
  private SecurityOauth2ClientDetailsService clientDetailsService;

  @Autowired
  private CustomSecurityWebResponseExceptionTranslator customSecurityWebResponseExceptionTranslator;

  @Autowired
  private Oauth2AuthExceptionEntryPoint authExceptionEntryPoint;


  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    CustomOauth2ClientCredentialsTokenEndpointFilter endpointFilter = new CustomOauth2ClientCredentialsTokenEndpointFilter(security);
    endpointFilter.afterPropertiesSet();
    endpointFilter.setAuthenticationEntryPoint(authExceptionEntryPoint);
    security.addTokenEndpointAuthenticationFilter(endpointFilter);

    security
        .authenticationEntryPoint(authExceptionEntryPoint)
        .tokenKeyAccess("isAuthenticated()")
        .checkTokenAccess("permitAll()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(clientDetailsService);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        // Token使用Redis进行存放
        .tokenStore(tokenStore())
        // 自定义TokenGranter
        .tokenGranter(new CustomBaseTokenGranter(endpoints,authenticationManager))
        // 允许授权模式：password的使用
        .authenticationManager(authenticationManager)
        // 允许get、post请求
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
        // 自定义异常处理
        .exceptionTranslator(customSecurityWebResponseExceptionTranslator)
        .userDetailsService(userDetailsService);
  }

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

}
