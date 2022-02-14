package com.zhty.inspect.entity.auth;

import com.zhty.inspect.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-08 10:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthClientDetails extends BaseEntity {

  private String client_id;
  private String resource_ids;
  private String client_secret;
  private String user_secret;
  private String scope;
  private String authorized_grant_types;
  private String web_server_redirect_uri;
  private String authorities;
  private Integer access_token_validity;
  private Integer refresh_token_validity;
  private String additional_information;
  private String autoapprove;

}
