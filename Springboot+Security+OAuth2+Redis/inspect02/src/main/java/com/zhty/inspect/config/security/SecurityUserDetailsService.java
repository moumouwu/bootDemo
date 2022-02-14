package com.zhty.inspect.config.security;

import com.zhty.inspect.config.exception.CustomSecurityOauth2Exception;
import com.zhty.inspect.entity.auth.AuthUserDetails;
import com.zhty.inspect.mapper.AuthMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.stereotype.Service;

/**
 * 自定义用户验证数据
 * @author Qin
 * @version 1.0
 * @date 2020-12-07 10:47
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

  @Autowired
  private AuthMapper authMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws CustomSecurityOauth2Exception {
    AuthUserDetails findUser = authMapper.findByUsername(username);
    if (findUser == null) {
      throw new CustomSecurityOauth2Exception(Values.INVALID_USERNAME);
    }
    return authUserDetails(findUser, Values.r_reg);
  }

  public static UserDetails authUserDetails(AuthUserDetails userDetails, String role) {
    List<SimpleGrantedAuthority> collect = new ArrayList<>();
    SimpleGrantedAuthority authority_role = new SimpleGrantedAuthority(role);
    collect.add(authority_role);
    if (userDetails.getRole()!=null&&!"".equals(userDetails.getRole().getName())){
      SimpleGrantedAuthority authority_scope = new SimpleGrantedAuthority(userDetails.getRole().getName());
      collect.add(authority_scope);
    }
    SecurityUserDetails<AuthUserDetails> result = new SecurityUserDetails(userDetails,userDetails.getUsername(), userDetails.getPassword(),
        userDetails.getLocked(),userDetails.getExpired(),userDetails.getCredExpired(),userDetails.getEnabled(),collect);
    return result;
  }

}
