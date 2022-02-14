package com.zhty.inspect.config.oauth2.handler;

import com.alibaba.fastjson.JSON;
import com.zhty.inspect.entity.R;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 14:38
 */
@Component
@Slf4j
public class Oauth2AccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException e) throws IOException, ServletException {
    response.setHeader("Content-Type", "application/json;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.write(JSON.toJSONString(R.forbidden("权限不足")));
  }
}
