package com.pay.demo.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-29 18:04
 */
public class Utils {

  /**
   * 生成订单号
   * @return
   */
  public static String generateOrderNo(){
    String time = getDateToString(new Date(), "yyyyMMddHHmmss");
    String random = RandomUtil.getNum(10);
    return time + random;
  }


  /**
   *
   * @param date Date类型参数
   * @param formatter 时间格式， 如 yyyy-MM-dd HH:mm:ss
   * @return
   */
  public static String getDateToString(Date date, String formatter) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
    LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    return dateTimeFormatter.format(localDateTime);
  }

}
