package com.zhty.inspect.config.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhty.inspect.config.exception.serializer.CustomSecurityOauth2ExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 10:34
 */
@JsonSerialize(using = CustomSecurityOauth2ExceptionSerializer.class)
    public class CustomSecurityOauth2Exception extends OAuth2Exception {


  public CustomSecurityOauth2Exception(String msg, Throwable t) {
    super(msg, t);
  }

  public CustomSecurityOauth2Exception(String msg) {
    super(msg);
  }
}
