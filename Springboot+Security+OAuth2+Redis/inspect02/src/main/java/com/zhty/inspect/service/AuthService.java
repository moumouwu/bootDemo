package com.zhty.inspect.service;

import com.zhty.inspect.entity.auth.AuthUserDetails;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-07 10:49
 */
public interface AuthService {

  /**
   * 根据登录名查询用户是否存在
   * @param username 登录名
   * @return 查询结果
   */
  AuthUserDetails findByUsername(String username);

  /**
   * 根据手机号查询用户记录
   * @param phone 手机号
   * @return
   */
  AuthUserDetails findByPhone(String phone);

}
