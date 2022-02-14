package com.zhty.inspect.entity;

import com.zhty.inspect.utils.Util;
import java.util.Date;

/**
 * 统一返回消息数据结构
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 14:12
 */
public class R<T> {

  /**
   * 状态码
   */
  private Integer code;

  /**
   * 消息
   */
  private String message;

  /**
   * 数据
   */
  private T data;

  /**
   * 状态描述
   */
  private String status;

  /**
   * 时间戳
   */
  private String timestamp;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTimestamp() {
    return Util.getDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
  }

  public R() {
  }

  public R(Integer code, String message, T data, String status) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.status = status;
  }

  /**
   * 成功
   * @return
   */
  public static R ok() {
    return new R<>(200, "请求成功", "", "success");
  }

  /**
   * 成功
   * @param msg 成功消息
   * @return
   */
  public static R ok(String msg) {
    return new R<>(200, msg, "", "success");
  }

  /**
   * 成功
   * @param result 返回的数据
   * @return
   */
  public static R ok(Object result) {
    return new R<>(200, "请求成功", result, "success");
  }

  /**
   * 成功
   * @param msg 成功消息
   * @param result 返回的数据
   * @return
   */
  public static R ok(String msg, Object result) {
    return new R<>(200, msg, result, "success");
  }

  /**
   * 失败
   * @return 返回默认数据
   */
  public static R error() {
    return new R<>(500, "请求失败", "", "error");
  }

  /**
   * 失败
   * @param msg 返回指定消息
   * @return
   */
  public static R error(String msg) {
    return new R<>(500, msg, "", "error");
  }

  /**
   * 失败
   * @param msg 返回指定消息
   * @param status 返回指定错误状态
   * @return
   */
  public static R error(String msg, String status) {
    return new R<>(500, msg, "", status);
  }

  /**
   * 401用户未授权
   * @return
   */
  public static R unauthorized() {
    return new R<>(401, "用户未授权", "", "unauthorized");
  }

  /**
   * 401用户未授权，详细错误信息可以放在data里面
   * @param result 详细错误信息
   * @return
   */
  public static R unauthorized(Object result) {
    return new R<>(401, "用户未授权", result, "unauthorized");
  }

  /**
   * 客户端请求方法被禁止，如需要POST请求，但客户端是GET请求
   * @return
   */
  public static R methodNotAllowed() {
    return new R<>(405, "请求方法不支持", "", "methodNotAllowed");
  }

  /**
   * 请求被拒绝
   * @return
   */
  public static R forbidden() {
    return new R<>(403, "请求被拒绝", "", "forbidden");
  }

  /**
   * 请求被拒绝
   * @param msg 错误消息
   * @return
   */
  public static R forbidden(String msg) {
    return new R<>(403, msg, "", "forbidden");
  }

  /**
   * 请求被拒绝
   * @param msg 错误消息
   * @param status 错误状态
   * @return
   */
  public static R forbidden(String msg, String status) {
    return new R<>(403, msg, "", status);
  }

  /**
   * 自定义消息类型
   * @param code 状态码
   * @param msg 消息
   * @param data 数据
   * @param status 状态描述
   * @return 返回结果
   */
  public static R custom(Integer code, String msg, Object data, String status) {
    return new R<>(code,msg,data,status);
  }

}
