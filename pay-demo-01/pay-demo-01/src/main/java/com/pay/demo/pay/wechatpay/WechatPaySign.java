package com.pay.demo.pay.wechatpay;

import com.pay.demo.pay.wechatpay.WechatPayConstants;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.util.Base64Utils;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-04-01 9:36
 */
@Slf4j
public class WechatPaySign {


  /**
   *
   * @param method
   * @param url
   * @param body
   * @return
   * @throws UnsupportedEncodingException
   */
  public String getToken(String method, HttpUrl url, String body)
      throws UnsupportedEncodingException {
    // 生成随机字符串
    String nonceStr = UUID.randomUUID().toString().replace("-", "");
    long timestamp = System.currentTimeMillis() / 1000;
    String message = buildMessage(method, url, timestamp, nonceStr, body);
    String signature = sign(message.getBytes("utf-8"));
    return "WECHATPAY2-SHA256-RSA2048 mchid=\"" + WechatPayConstants.mchId + "\","
        + "nonce_str=\"" + nonceStr + "\","
        + "timestamp=\"" + timestamp + "\","
        + "serial_no=\"" + WechatPayConstants.serialNo + "\","
        + "signature=\"" + signature + "\"";
  }

  /**
   * 构建签名所需字符串
   *
   * @param method     请求方法
   * @param url        HttpUrl
   * @param timestamp  时间戳
   * @param nonceStr   随机字符串
   * @param body       请求实体
   * @return
   */
  private String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
    String canonicalUrl = url.encodedPath();
    if (url.encodedQuery() != null) {
      canonicalUrl += "?" + url.encodedQuery();
    }
    String signStr = Stream.of(method, canonicalUrl, String.valueOf(timestamp), nonceStr, body)
        .collect(Collectors.joining("\n", "", "\n"));
    return signStr;
  }


  /**
   * 构建签名字符串
   *
   * @param message
   * @return
   */
  private String sign(byte[] message) {
    try {
      Signature signature = Signature.getInstance("SHA256withRSA");
      // 根据路径获取到PrivateKey
      // 从本地路径加载商品密钥
      InputStream privateKeyStream =  new FileInputStream(WechatPayConstants.keyPemPath);
      PrivateKey privateKey = PemUtil.loadPrivateKey(privateKeyStream);

      signature.initSign(privateKey);
      signature.update(message);
      return Base64.getEncoder().encodeToString(signature.sign());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (SignatureException e) {
      e.printStackTrace();
    }
    return null;
  }
}
