package com.zhty.inspect.service.impl;

import com.zhty.inspect.entity.auth.AuthUserDetails;
import com.zhty.inspect.mapper.AuthMapper;
import com.zhty.inspect.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-07 10:50
 */
@Service
public class IAuthServiceImpl implements AuthService {

  @Autowired
  private AuthMapper authMapper;

  @Override
  public AuthUserDetails findByUsername(String username) {
    return authMapper.findByUsername(username);
  }

  @Override
  public AuthUserDetails findByPhone(String phone) {
    return authMapper.findByPhone(phone);
  }

}
