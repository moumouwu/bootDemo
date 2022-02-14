package com.zhty.inspect.config.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * OAuth2一般异常处理
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 10:59
 */
public class CustomServerErrorException extends OAuth2Exception {

  public CustomServerErrorException(String msg, Throwable t) {
    super(msg, t);
  }

  public CustomServerErrorException(String msg) {
    super(msg);
  }
}
