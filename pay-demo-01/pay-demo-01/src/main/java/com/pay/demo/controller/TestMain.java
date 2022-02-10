package com.pay.demo.controller;

import com.alibaba.fastjson.JSON;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-04-01 9:40
 */
public class TestMain {

  public static void main(String[] args) {
    String reqdata = "{"
        + "\"time_expire\":\"2018-06-08T10:34:56+08:00\","
        + "\"amount\": {"
        + "\"total\":100,"
        + "\"currency\":\"CNY\""
        + "},"
        + "\"mchid\":\"1230000109\","
        + "\"description\":\"Image形象店-深圳腾大-QQ公仔\","
        + "\"notify_url\":\"https://www.weixin.qq.com/wxpay/pay.php\","
        + "\"out_trade_no\":\"1217752501201407033233368018\","
        + "\"goods_tag\":\"WXG\","
        + "\"appid\":\"wxd678efh567hg6787\","
        + "\"attach\":\"自定义数据说明\","
        + "\"detail\": {"
        + "\"invoice_id\":\"wx123\","
        + "\"goods_detail\": ["
        + "{"
        + "\"goods_name\":\"iPhoneX 256G\","
        + "\"wechatpay_goods_id\":\"1001\","
        + "\"quantity\":1,"
        + "\"merchant_goods_id\":\"商品编码\","
        + "\"unit_price\":828800"
        + "},"
        + "{"
        + "\"goods_name\":\"iPhoneX 256G\","
        + "\"wechatpay_goods_id\":\"1001\","
        + "\"quantity\":1,"
        + "\"merchant_goods_id\":\"商品编码\","
        + "\"unit_price\":828800"
        + "}"
        + "],"
        + "\"cost_price\":608800"
        + "},"
        + "\"scene_info\": {"
        + "\"store_info\": {"
        + "\"address\":\"广东省深圳市南山区科技中一道10000号\","
        + "\"area_code\":\"440305\","
        + "\"name\":\"腾讯大厦分店\","
        + "\"id\":\"0001\""
        + "},"
        + "\"device_id\":\"013467007045764\","
        + "\"payer_client_ip\":\"14.23.150.211\""
        + "}"
        + "}";
//    System.out.println(reqdata);
    BigDecimal bigDecimal = new BigDecimal("0.01");

  }
}
