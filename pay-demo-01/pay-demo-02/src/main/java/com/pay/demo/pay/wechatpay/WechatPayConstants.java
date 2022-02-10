package com.pay.demo.pay.wechatpay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-31 16:34
 */
@Component
public class WechatPayConstants {

  public static String appId = null;
  public static String mchId = null;
  public static String apiV3 = null;
  public static String serialNo = null;
  public static String keyPemPath = null;
  public static String platformCertPath = null;
  public static String payScanUrl = null;
  public static String notifyUrl = null;

  public static int SUCCESS_CODE = 200;
  public static int SUCCESS_CODE_NO_BODY = 204;

  /**
   * Native统一下单支付API
   */
  public static String payNativeUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
  /**
   * 统一查询API（使用商户系统订单号）
   */
  public static String payQueryUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/ORDER_NO?mchid=";
  /**
   * 统一关闭交易API
   */
  public static String payCloseUrl = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/ORDER_NO/close";
  /**
   * 退款API
   */
  public static String payRefundUrl = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds";

  /**
   * 退款查询API
   */
  public static String payQueryRefundUrl = "https://api.mch.weixin.qq.com/v3/refund/domestic/refunds/REFUND_NO";

  /**
   * 申请交易账单API
   */
  public static String downloadTradeUrl = "https://api.mch.weixin.qq.com/v3/bill/tradebill?bill_date=BILL_DATE&bill_type=BILL_TYPE";

  /**
   * 申请资金账单API
   */
  public static String downloadFundUrl = "https://api.mch.weixin.qq.com/v3/bill/fundflowbill?bill_date=BILL_DATE&account_type=ACCOUNT_TYPE";


  @Value("${pay.wechatpay.app_id}")
  public void setAppId(String appId) {
    WechatPayConstants.appId = appId;
  }

  @Value("${pay.wechatpay.mch_id}")
  public void setMchId(String mchId) {
    WechatPayConstants.mchId = mchId;
  }

  @Value("${pay.wechatpay.api_v3}")
  public void setApiV3(String apiV3) {
    WechatPayConstants.apiV3 = apiV3;
  }

  @Value("${pay.wechatpay.mch_serial_no}")
  public void setSerialNo(String serialNo) {
    WechatPayConstants.serialNo = serialNo;
  }

  @Value("${pay.wechatpay.api_client_key_pem}")
  public void setKeyPemPath(String keyPemPath) {
    WechatPayConstants.keyPemPath = keyPemPath;
  }

  @Value("${pay.wechatpay.platform_cert_pem}")
  public void setPlatformCertPath(String platformCertPath) {
    WechatPayConstants.platformCertPath = platformCertPath;
  }

  @Value("${pay.wechatpay.pay_page_url}")
  public void setPayScanUrl(String payScanUrl) {
    WechatPayConstants.payScanUrl = payScanUrl;
  }

  @Value("${pay.wechatpay.notify_url}")
  public void setNotifyUrl(String notifyUrl) {
    WechatPayConstants.notifyUrl = notifyUrl;
  }
}
