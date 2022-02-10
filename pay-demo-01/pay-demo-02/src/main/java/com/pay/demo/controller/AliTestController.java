package com.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.FileItem;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author binSin
 * @date 2021/8/6
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class AliTestController {

    //2021002166664270
    //P95c599ddafb347da81dbed2dbf07c34
    //202108BBde48649df0dd4538b392766af7ef2B34
    //202108BB8bcd001e64e94b358597d12523d68X34
    @GetMapping("/token/query")
    public String tokenQuery() throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("app_auth_token", "202108BBc1f6ff94a1634c3f82da0bddf56e9X02");
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.auth.token.app.query", null, bizParams);
        String httpBody = response.getHttpBody();
        Map map = JSON.parseObject(httpBody, Map.class);
        System.out.println(map.get("alipay_open_auth_token_app_query_response"));
        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }


    @GetMapping("/token")
    public String token() throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
//        bizParams.put("grant_type", "authorization_code");
//        bizParams.put("code", "P95c599ddafb347da81dbed2dbf07c34");
        bizParams.put("grant_type", "refresh_token");
        bizParams.put("refresh_token", "202108BB8bcd001e64e94b358597d12523d68X34");
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.auth.token.app", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

    @GetMapping("/create")
    public String create() throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setContact_email("1137535547@qq.com");
        contactInfo.setContact_mobile("18950278337");
        contactInfo.setContact_name("吴海斌");
        bizParams.put("account", "18950278337");
        bizParams.put("contact_info", contactInfo);
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.agent.create", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

    @GetMapping("/common")
    public String common(@RequestParam("batchNo") String batchNo) throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("batch_no", batchNo);
        bizParams.put("mcc_code", "C_C04_5311");
        FileItem businessLicensePic = new FileItem("F:/image/20210705104048.png");
        bizParams.put("business_license_pic", businessLicensePic);
        bizParams.put("business_license_no", "1532501100006302");
        bizParams.put("product_code", "FAST_INSTANT_TRADE_PAY");
        bizParams.put("web_sites", "zzy.xmlcitc.com");
        bizParams.put("account", "18950278337");
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.agent.common.sign", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("batchNo") String batchNo) throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("batch_no", batchNo);
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.agent.commonsign.confirm", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

    @GetMapping("/query")
    public String agent(@RequestParam("batchNo") String batchNo) throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("batch_no", batchNo);
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.agent.order.query", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

}
