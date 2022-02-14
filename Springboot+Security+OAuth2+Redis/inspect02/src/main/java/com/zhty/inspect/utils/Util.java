package com.zhty.inspect.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 14:21
 */
public class Util {

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
