package com.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.pay.demo.entity.OrderReq;
import com.pay.demo.pay.SignUtils;
import com.pay.demo.pay.alipay.AlipayConstants;
import com.pay.demo.pay.wechatpay.WechatPayConstants;
import com.pay.demo.pay.wechatpay.WechatPayInit;
import com.pay.demo.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author binSin
 * @date 2021/6/18 14:47
 */
@RestController
@Slf4j
@RequestMapping("/api/systemPayment")
public class WxPayment {

    @GetMapping("/mobile")
    public String mobile() throws Exception {
        Map<String, String> textParams = new HashMap<>();
//        textParams.put("app_auth_token", "201712BB_D0804adb2e743078d1822d536956X34");
        Map<String, Object> bizParams = new HashMap<>();
        bizParams.put("out_trade_no", Utils.generateOrderNo());
        bizParams.put("scene", "bar_code");
        bizParams.put("auth_code", "28763443825664394");
        bizParams.put("subject", "cxx");
//        bizParams.put("product_code", "QUICK_WAP_PAY");
        log.error("json:{}",JSON.toJSONString(bizParams));
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.trade.pay", textParams, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }


    @GetMapping("/create")
    public String create() throws Exception {
        Map<String, Object> bizParams = new HashMap<>();
        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("contact_name", "吴海斌");
        contactInfo.put("contact_mobile", "18950278337");
        contactInfo.put("contact_email", "1137535547@qq.com");
        bizParams.put("account", "18950278337");
        bizParams.put("contact_info", contactInfo);
        AlipayOpenApiGenericResponse response =
                Factory.Util.Generic().execute("alipay.open.agent.create", null, bizParams);

        log.info("支付宝:支付回调结果：{}", JSON.toJSONString(response));
        return JSON.toJSONString(response);
    }

    /**
     * 小程序支付
     *
     * @param order
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/wxPayment2")
    public String wxPayment2(@RequestBody OrderReq order, HttpServletRequest request) throws Exception {
        Map<String, Object> reqData = new HashMap<>(16);
        // appId
        reqData.put("appid", "wx88a73dc40d237324");
        reqData.put("mchid", WechatPayConstants.mchId);
        reqData.put("description", order.getSubject());
        // 订单号生成
        String orderNo = Utils.generateOrderNo();
        reqData.put("out_trade_no", orderNo);
        reqData.put("notify_url", WechatPayConstants.notifyUrl);
        Map<String, Object> amountMap = new HashMap<>(16);
        // 微信支付 最小金额单位为： 分  所以 0.01元 -> 1 分转换
        amountMap.put("total", order.getTotal().multiply(new BigDecimal(100)).intValue());
        amountMap.put("currency", "CNY");
        reqData.put("amount", amountMap);
        Map<String, Object> payerMap = new HashMap<>(16);
        payerMap.put("openid", "ot9mf5baUgByrDw66ozzoMrwpYOs");
        reqData.put("payer", payerMap);
        try {
            StringEntity entity = new StringEntity(JSON.toJSONString(reqData), "utf-8");
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            CloseableHttpResponse response = new WechatPayInit().getHttpClient().execute(httpPost);
//            log.info("微信支付统一下单回调结果：{}", JSON.toJSONString(response));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == WechatPayConstants.SUCCESS_CODE) {
                String result = EntityUtils.toString(response.getEntity());
                log.info("200状态下body: {}", result);
                // 返回前端数据
//                JSONObject payMap = new JSONObject();
                Map<String, String> payMap = new HashMap<>(16);
                long timestamp = System.currentTimeMillis() / 1000;
                String nonceStr = UUID.randomUUID().toString().replace("-", "");
                payMap.put("appId", "wx88a73dc40d237324");
                payMap.put("timeStamp", timestamp + "");
                payMap.put("nonceStr", nonceStr);
                payMap.put("package", "prepay_id=" + JSON.parseObject(result).get("prepay_id"));
                payMap.put("signType", "RSA");
                // 生成请求签名
                String jsonStr = "prepay_id=" + JSON.parseObject(result).get("prepay_id");
                String paySign = new SignUtils().getToken("wx88a73dc40d237324", nonceStr, timestamp, jsonStr);
                payMap.put("paySign", paySign);
                log.info("返回前端数据:{}", JSON.toJSONString(payMap));
                return JSON.toJSONString(payMap);
            } else if (statusCode == WechatPayConstants.SUCCESS_CODE_NO_BODY) {
                log.info("204状态下无body");
            } else {
                String errorResult = EntityUtils.toString(response.getEntity());
                log.info("其他状态码下body：{}", errorResult);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
