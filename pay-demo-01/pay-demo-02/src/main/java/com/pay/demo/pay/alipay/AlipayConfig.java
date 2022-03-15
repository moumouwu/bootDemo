package com.pay.demo.pay.alipay;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.pay.demo.controller.TestMain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author binSin
 * @date 2021/6/17 14:15
 */
@Configuration
public class AlipayConfig {

    @Value("${pay.alipay.app_id}")
    private String appId;

    /** 应用私钥 */
    @Value("${pay.alipay.app_private_key}")
    private String app_private_key;

    /** 应用公钥证书 */
    @Value("${pay.alipay.app_certPath}")
    private String mer_cert_path;

    /** 支付宝根证书 */
    @Value("${pay.alipay.ali_rootCertPath}")
    private String ali_root_cert_path;

    /** 支付宝公钥证书 */
    @Value("${pay.alipay.ali_public_CertPath}")
    private String ali_cert_path;

    /** 支付成功异步接口 */
    @Value("${pay.alipay.notify_url}")
    private String ali_notify_url;

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
        config.signType = "RSA2";
        config.appId = appId;

        config.merchantPrivateKey = TestMain.getPrivateKey(new ClassPathResource(app_private_key).getFile());
//        config.merchantPrivateKey = app_private_key;
        config.merchantCertPath = new ClassPathResource(mer_cert_path).getPath();
        config.alipayCertPath = new ClassPathResource(ali_cert_path).getPath();
        config.alipayRootCertPath = new ClassPathResource(ali_root_cert_path).getPath();
        config.notifyUrl = ali_notify_url;
        Factory.setOptions(config);
        return config;
    }


}
