package com.zhty.inspect.config.oauth2.granter.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.zhty.inspect.config.oauth2.granter.CustomBaseTokenGranter.CustomOauth2AccessToken;
import com.zhty.inspect.entity.R;
import java.io.IOException;

/**
 * 自定义 {@link CustomOauth2AccessToken} 序列化器
 * @author Qin
 * @version 1.0
 * @date 2020-12-22 14:50
 */
public class CustomOauth2AccessTokenSerializer extends StdSerializer<CustomOauth2AccessToken> {

  protected CustomOauth2AccessTokenSerializer() {
    super(CustomOauth2AccessToken.class);
  }

  @Override
  public void serialize(CustomOauth2AccessToken accessToken,
      JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeObject(R.ok("授权成功", accessToken.tokenSerialize()));
  }
}
