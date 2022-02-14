package com.zhty.inspect.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.zhty.inspect.entity.R;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 14:41
 */
@Component
@Slf4j
public class Oauth2AuthExceptionEntryPoint implements AuthenticationEntryPoint {

  private final static String InsufficientAuthenticationException = "InsufficientAuthenticationException";
  private final static String BadCredentialsException = "BadCredentialsException";

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException e)
      throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    String simpleClassName = e.getClass().getSimpleName();
    String message = "服务器错误";
    if (InsufficientAuthenticationException.equals(simpleClassName)) {
      Throwable cause = e.getCause();
      if (cause !=null) {
        if (cause instanceof InvalidTokenException) {
          message = "无效的ACCESS_TOKEN";
        }
        if (cause instanceof OAuth2AccessDeniedException) {
          message = "请求资源权限不足";
        }
      } else {
        message = "未携带ACCESS_TOKEN";
      }
    }
    if (BadCredentialsException.equals(simpleClassName)) {
      message = "客户端凭据错误";
    }
    R result = R.custom(400, message, "", "error");
    out.write(JSON.toJSONString(result));
  }
}
