package com.zhty.inspect.mapper;

import com.zhty.inspect.entity.auth.AuthUserDetails;
import com.zhty.inspect.entity.auth.OauthClientDetails;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-15 17:32
 */
public interface AuthMapper {

  /**
   * 根据登录名查询用户记录
   * @param username 登录名
   * @return 返回查询结果
   */
  AuthUserDetails findByUsername(String username);

  /**
   * 根据手机号查询用户记录
   * @param phone 手机号
   * @return
   */
  AuthUserDetails findByPhone(String phone);

  /**
   * 根据微信openid查询用户记录
   * @param openid 微信openid
   * @return
   */
  AuthUserDetails findByWechatOpenid(String openid);

  /**
   * 根据clientId查询是否存在指定客户端
   * @param clientId 客户端ID
   * @return 查询结果
   */
  OauthClientDetails findByClientId(String clientId);

}
