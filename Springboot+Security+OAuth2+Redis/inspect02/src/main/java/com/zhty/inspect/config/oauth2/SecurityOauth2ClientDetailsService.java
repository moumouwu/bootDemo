package com.zhty.inspect.config.oauth2;

import com.zhty.inspect.entity.auth.OauthClientDetails;
import com.zhty.inspect.mapper.AuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 10:28
 */
@Service
@Slf4j
public class SecurityOauth2ClientDetailsService implements ClientDetailsService {

  @Autowired
  private AuthMapper authMapper;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    if (clientId == null || StringUtils.isEmpty(clientId)) {
      // 未携带client_id
      throw new ClientRegistrationException("未携带client_id");
    }
    OauthClientDetails findClient = authMapper.findByClientId(clientId);
    if (findClient==null) {
      throw new ClientRegistrationException("未匹配到对应client_id:"+clientId);
//      return null;
    }
    SecurityClientsDetails securityClients = new SecurityClientsDetails(findClient.getClient_id(),findClient.getClient_secret(),findClient.getResource_ids(),findClient.getScope()
    ,findClient.getAuthorized_grant_types(),findClient.getWeb_server_redirect_uri(),findClient.getAuthorities(),findClient.getAccess_token_validity(),findClient.getRefresh_token_validity()
        ,findClient.getAdditional_information(),findClient.getAutoapprove());
     return securityClients;
  }
}
