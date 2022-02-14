package com.zhty.inspect.config.oauth2.granter;

import com.zhty.inspect.config.exception.CustomSecurityOauth2Exception;
import com.zhty.inspect.config.security.SecurityUserDetails;
import com.zhty.inspect.config.security.SecurityUserDetailsService;
import com.zhty.inspect.config.security.Values;
import com.zhty.inspect.config.util.ApplicationContextAwareUtil;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 微信小程序登录
 * @author Qin
 * @version 1.0
 * @date 2020-12-18 15:28
 */
public class WeChatMiniAppTokenGranter extends AbstractTokenGranter {
  private static final String GRANT_TYPE = "wechat_miniapp";

  private AuthMapper authMapper = ApplicationContextAwareUtil.getBean("authMapper");

  public WeChatMiniAppTokenGranter(
      AuthorizationServerTokenServices tokenServices,
      ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  @SuppressWarnings("Duplicates")
  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
    // 获取参数，自定义校验
    Map<String, String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
    // 用户名 = 微信小程序获取到的 code
    String code = parameters.get("username");
    if (code == null || "".equals(code)) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_WECHAT_CODE);
    }
    // 从微信接口拿到openid
    // ...


    // ...
    String openId = "openId1";
    if (openId == null) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_WECHAT_OPENID);
    }
    AuthUserDetails user = authMapper.findByWechatOpenid(openId);
    if (user == null) {
      // 表明系统没有该用户信息，微信小程序普通用户
      List<SimpleGrantedAuthority> collect = new ArrayList<>();
      SimpleGrantedAuthority authority_role = new SimpleGrantedAuthority(Values.r_unreg);
      collect.add(authority_role);
      // 设置基本信息
      AuthUserDetails baseInfo = new AuthUserDetails();
      baseInfo.setId(-1L);
      baseInfo.setNickname("普通用户_" + openId);
      baseInfo.setWechatOpenId(openId);
      baseInfo.setUsername(openId);
      baseInfo.setPassword(new BCryptPasswordEncoder().encode(openId));
      SecurityUserDetails userDetails = new SecurityUserDetails(baseInfo,baseInfo.getUsername(), baseInfo.getPassword(),0,0,0,0, collect);
      Authentication userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
      OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    } else {
      UserDetails userDetails = SecurityUserDetailsService.authUserDetails(user, Values.r_reg);
      Authentication userAuth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
      ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
      OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
  }
}
