package com.zhty.inspect.config.security;

/**
 * 固定变量
 * @author Qin
 * @version 1.0
 * @date 2020-12-21 15:46
 */
public class Values {

  /**
   * 已注册
   */
  public static String reg = "REGISTERED";

  /**
   * 未注册
   */
  public static String unreg = "UNREGISTERED";

  /**
   * 在hasRole方法中，需要前缀: ROLE_
   */
  public static String r_reg = "ROLE_REGISTERED";
  public static String r_unreg = "ROLE_UNREGISTERED";

  /**
   * 用户名无效
   */
  public static String INVALID_USERNAME = "invalid_username";

  /**
   * 用户凭据无效
   */
  public static String INVALID_CRED = "invalid_cred";

  /**
   * 用户验证码过期
   */
  public static String INVALID_SMS_EXPIRED = "invalid_sms_expired";

  /**
   * 用户验证码过期
   */
  public static String INVALID_SMS_ERROR = "invalid_sms_error";

  /**
   * 用户微信小程序openid无效
   */
  public static String INVALID_WECHAT_OPENID = "invalid_wechat_openid";

  /**
   * 用户微信小程序code无效
   */
  public static String INVALID_WECHAT_CODE = "invalid_wechat_code";

  /**
   * 未支持授权类型
   */
  public static String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";

  /**
   * 其他类型错误
   */
  public static String UNKNOWN_SERVER_ERROR = "unknown_server_error";
}
