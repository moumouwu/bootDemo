package com.zhty.inspect.config.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 9:55
 */
public class SecurityUserDetails<T> implements UserDetails {

  private T userInfo;
  private String username;
  private String password;
  private Integer locked;
  private Integer expired;
  private Integer credExpired;
  private Integer enabled;
  private Collection<? extends GrantedAuthority> authorities;

  public SecurityUserDetails() {}

  public SecurityUserDetails(T userInfo,String username, String password,
      Integer locked, Integer expired, Integer credExpired, Integer enabled,
      Collection<? extends GrantedAuthority> authorities) {
    this.userInfo = userInfo;
    this.username = username;
    this.password = password;
    this.locked = locked;
    this.expired = expired;
    this.credExpired = credExpired;
    this.enabled = enabled;
    this.authorities = authorities;
  }

  public T getUserInfo() {
    return userInfo;
  }

  public void setUserInfo(T userInfo) {
    this.userInfo = userInfo;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return expired==0;
  }

  @Override
  public boolean isAccountNonLocked() {
    return locked==0;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credExpired==0;
  }

  @Override
  public boolean isEnabled() {
    return enabled==0;
  }
}
