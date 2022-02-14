package com.zhty.inspect.entity.auth;

import com.zhty.inspect.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-21 16:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRole extends BaseEntity {

  private String name;
  private String description;

}
