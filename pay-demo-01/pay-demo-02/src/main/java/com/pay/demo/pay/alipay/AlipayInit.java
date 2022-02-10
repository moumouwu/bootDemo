package com.pay.demo.pay.alipay;//package com.pay.demo.pay.alipay;
//
//import com.alipay.easysdk.factory.Factory;
//import com.alipay.easysdk.kernel.Config;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * @author Qin
// * @version 1.0
// * @date 2021-03-29 17:14
// */
//@Slf4j
//@Component
//public class AlipayInit implements ApplicationRunner {
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Factory.setOptions(getConfig());
//        log.info("alipay sdk loading finished!");
//    }
//
//    private Config getConfig() {
//        Config config = new Config();
//        config.protocol = "https";
//        config.gatewayHost = AlipayConstants.gatewayUrl;
//        config.signType = AlipayConstants.signType;
//        config.appId = AlipayConstants.appId;
//        config.merchantPrivateKey = AlipayConstants.appPrivateKey;
////        config.merchantCertPath = new ClassPathResource(AlipayConstants.appCertPath).getPath();
////        config.alipayCertPath = new ClassPathResource(AlipayConstants.aliPublicCertPath).getPath();
////        config.alipayRootCertPath = new ClassPathResource(AlipayConstants.aliRootCertPath).getPath();
//        config.merchantCertPath = AlipayConstants.appCertPath;
//        config.alipayCertPath = AlipayConstants.aliPublicCertPath;
//        config.alipayRootCertPath = AlipayConstants.aliRootCertPath;
//
//
////    config.merchantPrivateKey = AlipayConstants.privateKey;
////    config.alipayPublicKey = AlipayConstants.publicKey;
//        config.notifyUrl = AlipayConstants.notifyUrl;
////        config.encryptKey = AlipayConstants.encryptKey;
//        return config;
//    }
//}
