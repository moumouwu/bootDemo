package com.zhty.inspect.config.exception.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zhty.inspect.config.exception.CustomSecurityOauth2Exception;
import com.zhty.inspect.config.security.Values;
import com.zhty.inspect.entity.R;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 格式化授权过程中产生的异常信息，转成JSON数据格式回调
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 10:35
 */
@Slf4j
public class CustomSecurityOauth2ExceptionSerializer extends StdSerializer<CustomSecurityOauth2Exception> {

  protected CustomSecurityOauth2ExceptionSerializer() {
    super(CustomSecurityOauth2Exception.class);
  }

  @Override
  public void serialize(CustomSecurityOauth2Exception e, JsonGenerator generator,
      SerializerProvider provider) throws IOException {

    String error_msg = e.getMessage()!=null?e.getMessage():Values.UNKNOWN_SERVER_ERROR;
    log.error("error_msg:{}",error_msg);
    generator.writeObject(R.custom(e.getHttpErrorCode(), getError(error_msg), "", "error"));
  }

  /**
   * 使用Map<String,String>包装错误信息，通过get()获取对应键值，避免多个if else
   * @param key 键
   * @return
   */
  private static String getError(String key) {
    Map<String,String> errorMap = new HashMap<>(16);
    errorMap.put(Values.INVALID_USERNAME, "用户名错误");
    errorMap.put(Values.INVALID_CRED, "凭据错误");
    errorMap.put(Values.INVALID_SMS_ERROR, "短信验证码错误");
    errorMap.put(Values.INVALID_SMS_EXPIRED, "短信验证码已过期");
    errorMap.put(Values.INVALID_WECHAT_CODE, "微信小程序code错误");
    errorMap.put(Values.INVALID_WECHAT_OPENID,"微信小程序openid错误");
    errorMap.put(Values.UNSUPPORTED_GRANT_TYPE, "授权类型不支持");
    errorMap.put(Values.UNKNOWN_SERVER_ERROR, "服务器异常");
    return errorMap.get(key);
  }
}
