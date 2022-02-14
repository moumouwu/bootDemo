package com.zhty.inspect.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-11-30 16:59
 */
@Setter
@Getter
public class BaseEntity implements Serializable {

  private Long id;
  private Date createTime;
  private Date updateTime;
}
