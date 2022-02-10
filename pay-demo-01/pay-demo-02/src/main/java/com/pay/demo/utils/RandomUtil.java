package com.pay.demo.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数验证码
 * @author Qin
 * @version 1.0
 * @date 2020-12-17 11:00
 */
public class RandomUtil {

  /**
   * 根据自定长度得到数字+字母+特殊字符随机数
   * 最短4位
   * @param length 生成字符串的长度
   * @return
   */
  public static String getNumAlpSymbol(int length){
    String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()_";
    return getRandomCode(length, source);
  }


  /**
   * 根据自定长度得到数字+大写字母随机数
   * 最短4位
   * @param length 生成随机数的长度
   * @return
   */
  public static String getNumAlp(int length){
    String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return getRandomCode(length, source);
  }

  /**
   * 根据自定长度得到纯大写字母随机数
   * 最短4位
   * @param length 生成随机数的长度
   * @return
   */
  public static String getAlp(int length){
    String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    return getRandomCode(length, source);
  }

  /**
   * 根据自定长度得到纯数字随机数
   * 最短4位
   * @param length 生成随机数的长度
   * @return
   */
  public static String getNum(int length){
    StringBuffer buffer = new StringBuffer();
    length = length<4?4:length;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i=0;i<length;i++){
      buffer.append(random.nextInt(10));
    }
    return buffer.toString();
  }

  /**
   * 根据长度和字符串生成随机字符串
   * 最短4位
   * @param length 生成随机数的长度
   * @param source 用于生成字符串的数据集
   * @return
   */
  public static String getRandomCode(int length, String source) {
    StringBuffer buffer = new StringBuffer();
    length = length<4?4:length;
    ThreadLocalRandom random = ThreadLocalRandom.current();
    for (int i=0;i<length;i++){
      buffer.append(source.charAt(random.nextInt(source.length())));
    }
    return buffer.toString();
  }
}
