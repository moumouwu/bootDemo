package com.zhty.inspect.entity.auth;

import com.zhty.inspect.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-07 10:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDetails extends BaseEntity {

  /**
   * 手机号
   */
  private String phone;

  /**
   * 微信openid
   */
  private String wechatOpenId;

  /**
   * 登录名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 性别
   */
  private Integer gender;

  /**
   * 唯一编号
   */
  private String uniqueId;

  /**
   * 昵称
   */
  private String nickname;

  /**
   * 账号是否锁定 0：未锁定，1：锁定
   */
  private Integer locked;

  /**
   * 账号是否过期 0：未过期，1：过期
   */
  private Integer expired;

  /**
   * 账号密码是否过期 0：未过期，1：过期
   */
  private Integer credExpired;

  /**
   * 账号是否被禁用 0：未禁用，1：禁用
   */
  private Integer enabled;

  /**
   * 权限
   */
  private AuthRole role;

}
