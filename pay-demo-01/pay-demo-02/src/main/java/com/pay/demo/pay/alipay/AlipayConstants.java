package com.pay.demo.pay.alipay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-30 9:31
 */
@Component
public class AlipayConstants {

    public static String appId = null;

//  public static String privateKey = null;
//
//  public static String publicKey = null;

    public static String appPrivateKey = null;
    public static String appCertPath = null;
    public static String aliRootCertPath = null;
    public static String aliPublicCertPath = null;

    public static String notifyUrl = null;

    public static String signType = null;

    public static String gatewayUrl = null;

    public static String encryptKey = null;

    public static String BILL_TYPE_TRADE = "trade";

    public static String BILL_TYPE_SIGNCUSTOMER = "signcustomer";


    @Value("${pay.alipay.app_id}")
    public void setAppId(String appId) {
        AlipayConstants.appId = appId;
    }


    @Value("${pay.alipay.app_private_key}")
    public static void setAppPrivateKey(String appPrivateKey) {
        AlipayConstants.appPrivateKey = appPrivateKey;
    }

    @Value("${pay.alipay.app_certPath}")
    public static void setAppCertPath(String appCertPath) {
        AlipayConstants.appCertPath = appCertPath;
    }

    @Value("${pay.alipay.ali_rootCertPath}")
    public static void setAliRootCertPath(String aliRootCertPath) {
        AlipayConstants.aliRootCertPath = aliRootCertPath;
    }

    @Value("${pay.alipay.ali_public_CertPath}")
    public static void setAliPublicCertPath(String aliPublicCertPath) {
        AlipayConstants.aliPublicCertPath = aliPublicCertPath;
    }
    //  @Value("${pay.alipay.merchant_private_key}")
//  public void setPrivateKey(String privateKey) {
//    AlipayConstants.privateKey = privateKey;
//  }
//
//  @Value("${pay.alipay.alipay_public_key}")
//  public void setPublicKey(String publicKey) {
//    AlipayConstants.publicKey = publicKey;
//  }

    @Value("${pay.alipay.notify_url}")
    public void setNotifyUrl(String notifyUrl) {
        AlipayConstants.notifyUrl = notifyUrl;
    }

    @Value("${pay.alipay.sign_type}")
    public void setSignType(String signType) {
        AlipayConstants.signType = signType;
    }

    @Value("${pay.alipay.gateway_url}")
    public void setGatewayUrl(String gatewayUrl) {
        AlipayConstants.gatewayUrl = gatewayUrl;
    }

    @Value("${pay.alipay.encrypt_key}")
    public void setEncryptKey(String encryptKey) {
        AlipayConstants.encryptKey = encryptKey;
    }
}
