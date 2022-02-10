package com.pay.demo.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-29 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq {

  /**
   * 商品名称
   */
  private String subject;

  /**
   * 总支付金额
   */
  private BigDecimal total = BigDecimal.ZERO;
}
