package com.zhty.inspect.utils;

import org.springframework.util.DigestUtils;

/**
 * md5工具类，使用springboot自带的方法生成
 * @author Qin
 * @version 1.0
 * @date 2020-12-21 14:50
 */
public class Md5Util {

  /**
   * md5加密
   * @param plaintext 明文
   * @return
   */
  public static String md5(String plaintext) {
    return generate(plaintext, "");
  }

  /**
   * md5加密，带盐加密
   * @param plaintext 明文
   * @param salt 盐
   * @return
   */
  public static String md5(String plaintext, String salt) {
    return generate(plaintext, salt);
  }

  private static String generate(String plaintext, String salt) {
    String password = (plaintext+ salt).replace(" ", "");
    if (password == null || "".equals(password)) {
      password = "123456";
    }
    return DigestUtils.md5DigestAsHex(password.getBytes());
  }
}
