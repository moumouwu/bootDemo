package com.pay.demo.pay.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author binSin
 * @date 2021/6/17 14:15
 */
@Configuration
public class AlipayConfig {

    @Value("${pay.alipay.app_id}")
    private String appId;

    /**
     * 应用私钥
     */
    @Value("${pay.alipay.app_private_key}")
    private String privateKey;

    /**
     * 应用公钥证书
     */
    @Value("${pay.alipay.app_certPath}")
    private String merCertPath;

    /**
     * 支付宝根证书
     */
    @Value("${pay.alipay.ali_rootCertPath}")
    private String aliRootCertPath;
    /**
     * 支付宝公钥证书
     */
    @Value("${pay.alipay.ali_public_CertPath}")
    private String aliCertPath;

    /**
     * 支付成功异步接口
     */
    @Value("${pay.alipay.notify_url}")
    private String aliNotifyUrl;

    @Value("${pay.alipay.protocol}")
    private String protocol;

    @Value("${pay.alipay.gateway_url}")
    private String gatewayHost;

    @Value("${pay.alipay.sign_type}")
    private String signType;

    @Value("${pay.alipay.encrypt_key}")
    private String encryptKey;


    @Bean
    public Config ConfigAli() throws Exception {

        Config config = new Config();
        config.protocol = protocol;
        config.gatewayHost = gatewayHost;
        config.signType = signType;
        config.appId = appId;
        config.merchantPrivateKey = privateKey;
        config.merchantCertPath = merCertPath;
        config.alipayCertPath = aliCertPath;
        config.alipayRootCertPath = aliRootCertPath;
        config.notifyUrl = aliNotifyUrl;
        config.encryptKey = encryptKey;
        Factory.setOptions(config);
        return config;
    }
}