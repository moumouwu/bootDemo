package com.pay.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-03-29 17:37
 */
@Controller
@Slf4j
public class ThymeleafController {

  @RequestMapping("/index")
  public String toIndex(){
    log.info("访问首页");
    return "index";
  }


  @RequestMapping("/pay/alipay/return")
  public String toAlipayReturn() {
    log.info("支付宝支付-访问回调页面");
    return "return";
  }

}
