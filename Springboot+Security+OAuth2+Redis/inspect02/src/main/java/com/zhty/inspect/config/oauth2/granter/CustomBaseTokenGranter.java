package com.zhty.inspect.config.oauth2.granter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhty.inspect.config.oauth2.granter.serializer.CustomOauth2AccessTokenSerializer;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.CollectionUtils;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 17:33
 */
@Slf4j
public class CustomBaseTokenGranter implements TokenGranter {

  /**
   * 委托 {@link CompositeTokenGranter}
   */
  private final CompositeTokenGranter delegate;

  public CustomBaseTokenGranter(AuthorizationServerEndpointsConfigurer configurer, AuthenticationManager authenticationManager) {
    final ClientDetailsService clientDetailsService = configurer.getClientDetailsService();
    final AuthorizationServerTokenServices tokenServices = configurer.getTokenServices();
    final AuthorizationCodeServices authorizationCodeServices = configurer.getAuthorizationCodeServices();
    final OAuth2RequestFactory requestFactory = configurer.getOAuth2RequestFactory();

    this.delegate = new CompositeTokenGranter(Arrays.asList(
        // 加入默认的授权类型
        new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory),
        new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory),
        new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory),
        new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory),
        new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory),
        // 加入自定义的授权类型
        new MobileTokenGranter(tokenServices,clientDetailsService,requestFactory),
        new SMSCodeTokenGranter(tokenServices,clientDetailsService,requestFactory),
        new WeChatMiniAppTokenGranter(tokenServices,clientDetailsService,requestFactory)
    ));
  }

  @Override
  public OAuth2AccessToken grant(String grant_type, TokenRequest tokenRequest) {
    final OAuth2AccessToken oAuth2AccessToken = Optional.ofNullable(delegate.grant(grant_type, tokenRequest))
        .orElseThrow(() -> new UnsupportedGrantTypeException("unsupported grant type"));
    return new CustomOauth2AccessToken(oAuth2AccessToken);
  }

  @JsonSerialize(using = CustomOauth2AccessTokenSerializer.class)
  public static final class CustomOauth2AccessToken extends DefaultOAuth2AccessToken {
    public CustomOauth2AccessToken(
        OAuth2AccessToken accessToken) {
      super(accessToken);
    }

    /**
     * 序列化{@link OAuth2AccessToken}
     *
     * @return
     */
    @SneakyThrows
    public Map<Object, Object> tokenSerialize() {
      final LinkedHashMap<Object, Object> map = new LinkedHashMap<>(5);
      map.put(OAuth2AccessToken.ACCESS_TOKEN, this.getValue());
      map.put(OAuth2AccessToken.TOKEN_TYPE, this.getTokenType());

      final OAuth2RefreshToken refreshToken = this.getRefreshToken();
      if (Objects.nonNull(refreshToken)) {
        map.put(OAuth2AccessToken.REFRESH_TOKEN, refreshToken.getValue());
      }

      final Date expiration = this.getExpiration();
      if (Objects.nonNull(expiration)) {
        map.put(OAuth2AccessToken.EXPIRES_IN, (expiration.getTime() - System.currentTimeMillis()) / 1000);
      }

      final Set<String> scopes = this.getScope();
      if (!CollectionUtils.isEmpty(scopes)) {
        final StringBuffer buffer = new StringBuffer();
        scopes.stream().filter(StringUtils::isNotBlank).forEach(scope -> buffer.append(scope).append(" "));
        map.put(OAuth2AccessToken.SCOPE, buffer.substring(0, buffer.length() - 1));
      }

      final Map<String, Object> additionalInformation = this.getAdditionalInformation();
      if (!CollectionUtils.isEmpty(additionalInformation)) {
        additionalInformation.forEach((key, value) -> map.put(key, additionalInformation.get(key)));
      }
      return map;
    }
  }

}
