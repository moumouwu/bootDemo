package com.zhty.inspect.config.oauth2;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 11:12
 */
public class SecurityClientsDetails implements ClientDetails {

  private String clientId;
  @Override
  public String getClientId() {
    return clientId;
  }
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  private String clientSecret;
  @Override
  public String getClientSecret() {
    return clientSecret;
  }
  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }
  @Override
  public boolean isSecretRequired() {
    return clientSecret != null;
  }

  private String resourceIds;
  @Override
  public Set<String> getResourceIds() {
    return get(resourceIds);
  }
  public void setResourceIds(String resourceIds) {
    this.resourceIds = resourceIds;
  }


  private String scope;
  @Override
  public Set<String> getScope() {
    return get(scope);
  }
  public void setScope(String scope) {
    this.scope = scope;
  }
  @Override
  public boolean isScoped() {
    return scope!=null&&scope.trim().length()>0;
  }

  private String authorizedGrantTypes;
  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return get(authorizedGrantTypes);
  }
  public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
    this.authorizedGrantTypes = authorizedGrantTypes;
  }

  private String registeredRedirectUris;
  @Override
  public Set<String> getRegisteredRedirectUri() {
    return get(registeredRedirectUris);
  }
  public void setRegisteredRedirectUri(String registeredRedirectUri) {
    this.registeredRedirectUris = registeredRedirectUris;
  }

  private String authorities;
  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    if (authorities==null||authorities.trim().length()<=0){
      return Collections.emptyList();
    }
    List<GrantedAuthority> authorityList = new ArrayList<>();
    String[] authoritiesArray = authorities.split(",");
    for (String authorities : authoritiesArray) {
      authorityList.add((GrantedAuthority) () -> authorities);
    }
    return authorityList;
  }
  public void setAuthorities(String authorities) {
    this.authorities = authorities;
  }

  private Integer accessTokenValiditySeconds;
  @Override
  public Integer getAccessTokenValiditySeconds() {
    return accessTokenValiditySeconds;
  }
  public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
  }



  private Integer refreshTokenValiditySeconds;
  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return refreshTokenValiditySeconds;
  }
  public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
    this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
  }

  private String additionalInformation;
  @Override
  public Map<String, Object> getAdditionalInformation() {
    if (additionalInformation==null||additionalInformation.trim().length()<=0){
      return Collections.emptyMap();
    }
    return JSON.parseObject(additionalInformation);
  }
  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  private String autoApproveScopes;
  public Set<String> getAutoApproveScopes() {
    return get(autoApproveScopes);
  }
  public void setAutoApproveScopes(String autoApproveScopes) {
    this.autoApproveScopes = autoApproveScopes;
  }

  @Override
  public boolean isAutoApprove(String scope) {
    if(getAutoApproveScopes() == null||getAutoApproveScopes().isEmpty()) {
      return false;
    } else {
      Iterator var2 = getAutoApproveScopes().iterator();
      String auto;
      do {
        if(!var2.hasNext()) {
          return false;
        }
        auto = (String)var2.next();
      } while(!auto.equals("true") && !scope.matches(auto));
      return true;
    }
  }

  public SecurityClientsDetails(String clientId, String clientSecret, String resourceIds,
      String scope, String authorizedGrantTypes, String registeredRedirectUris,
      String authorities, Integer accessTokenValiditySeconds,
      Integer refreshTokenValiditySeconds, String additionalInformation,
      String autoApproveScopes) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.resourceIds = resourceIds;
    this.scope = scope;
    this.authorizedGrantTypes = authorizedGrantTypes;
    this.registeredRedirectUris = registeredRedirectUris;
    this.authorities = authorities;
    this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    this.additionalInformation = additionalInformation;
    this.autoApproveScopes = autoApproveScopes;
  }


  private Set<String> get(String sources) {
    if (sources==null||sources.trim().length()<=0){
      return Collections.emptySet();
    }
    Set<String> set = new HashSet<>();
    String[] split = sources.split(",");
    for (String source : split) {
      set.add(source);
    }
    return set;
  }
}
