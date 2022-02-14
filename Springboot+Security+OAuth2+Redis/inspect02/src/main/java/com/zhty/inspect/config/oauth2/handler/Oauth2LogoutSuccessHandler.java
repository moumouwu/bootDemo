package com.zhty.inspect.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.zhty.inspect.entity.R;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 退出系统处理
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 17:03
 */
@Component
public class Oauth2LogoutSuccessHandler implements LogoutSuccessHandler {

  @Autowired
  private TokenStore tokenStore;

  private final static String TOKEN_TYPE = "bearer";

  @Override
  public void onLogoutSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    response.setHeader("Content-Type","application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String auth = request.getHeader("Authorization");
    if (!StringUtils.isEmpty(auth) && auth.contains(TOKEN_TYPE)){
      String access_token = auth.replace(TOKEN_TYPE, "");
      if (!StringUtils.isEmpty(access_token)) {
        OAuth2AccessToken auth2AccessToken = tokenStore.readAccessToken(access_token);
        if (auth2AccessToken!=null) {
          tokenStore.removeAccessToken(auth2AccessToken);
          OAuth2RefreshToken oAuth2RefreshToken = auth2AccessToken.getRefreshToken();
          tokenStore.removeRefreshToken(oAuth2RefreshToken);
          tokenStore.removeAccessTokenUsingRefreshToken(oAuth2RefreshToken);
        }
      }
    }
    out.write(JSON.toJSONString(R.ok("退出系统成功")));
  }
}
