package com.zhty.inspect.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-17 11:52
 */
public class RegValidUtil {

  private static final Pattern PHONE_REG = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");

  /**
   * 11位手机号格式
   * @param phone 手机号
   * @return
   */
  public static Boolean isValidPhone(String phone){
    Matcher matcher = PHONE_REG.matcher(phone);
    return matcher.matches();
  }
}
