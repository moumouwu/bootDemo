package com.pay.demo.pay.wechatpay;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-31 17:56
 */
public class WechatPayInit {


  public CloseableHttpClient getHttpClient() throws IOException {

    // 从本地路径加载商品密钥
    InputStream privateKeyStream =  new FileInputStream(WechatPayConstants.keyPemPath);
    PrivateKey privateKey = PemUtil.loadPrivateKey(privateKeyStream);

    // 自动更新证书
    // 将在原CertificatesVerifier上增加的证书的“超时自动更新”（默认与上次更新时间超过一小时后自动更新）
    // 并且在首次创建时，进行证书更新
    AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
        new WechatPay2Credentials(WechatPayConstants.mchId,
            new PrivateKeySigner(WechatPayConstants.serialNo, privateKey)),
        WechatPayConstants.apiV3.getBytes(StandardCharsets.UTF_8)
    );

    // 通过WechatPayHttpClientBuilder构建HttpClient，会自动处理签名和验签，并进行证书自动更新
    WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
        .withMerchant(WechatPayConstants.mchId, WechatPayConstants.serialNo, privateKey)
        .withValidator(new WechatPay2Validator(verifier));
    CloseableHttpClient httpClient = builder.build();

    return httpClient;
  }

}
