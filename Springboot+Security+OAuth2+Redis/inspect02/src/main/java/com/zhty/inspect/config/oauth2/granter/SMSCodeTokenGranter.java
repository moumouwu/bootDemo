package com.zhty.inspect.config.oauth2.granter;

import com.zhty.inspect.config.exception.CustomSecurityOauth2Exception;
import com.zhty.inspect.config.redis.RedisUtils;
import com.zhty.inspect.config.security.SecurityUserDetails;
import com.zhty.inspect.config.security.SecurityUserDetailsService;
import com.zhty.inspect.config.security.Values;
import com.zhty.inspect.config.util.ApplicationContextAwareUtil;
import com.zhty.inspect.controller.AuthController;
import com.zhty.inspect.entity.auth.AuthUserDetails;
import com.zhty.inspect.mapper.AuthMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 手机号+短信验证码登录
 * @author Qin
 * @version 1.0
 * @date 2020-12-17 9:37
 */
public class SMSCodeTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "sms_code";

  private AuthMapper authMapper = ApplicationContextAwareUtil.getBean("authMapper");

  private RedisUtils redisTemplate = ApplicationContextAwareUtil.getBean("redisUtils");

  public SMSCodeTokenGranter(
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  @SuppressWarnings("Duplicates")
  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
    Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
    String username = parameters.get("username");
    String password = parameters.get("password");
    AuthUserDetails user = authMapper.findByPhone(username);
    if (user == null) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_USERNAME);
    }
    String key = AuthController.SMS_CODE_PREFIX + username;
    if (!redisTemplate.hasKey(key)) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_SMS_EXPIRED);
    }
    String redisSmsCode = redisTemplate.get(key).toString();
    if (!redisSmsCode.equals(password)) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_SMS_ERROR);
    }
    UserDetails userDetails = SecurityUserDetailsService.authUserDetails(user, Values.r_reg);
    Authentication userAuth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
    OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
    return new OAuth2Authentication(storedOAuth2Request, userAuth);
  }
}
