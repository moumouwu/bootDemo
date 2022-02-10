package com.pay.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

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

  public static void main(String[] args) throws IOException {

    File file = new File("example"); //相对路径

    System.out.println(file.getAbsolutePath()); //获取绝对路径

    System.out.println(file.getName()); //获取名称

    System.out.println(file.exists()); //判断文件或文件夹是否存在

    boolean result = file.mkdirs();// 把 example 当成文件夹来创建,mkdirs()为级联创建
    System.out.println(result);

    result = file.createNewFile();// 把 example 当成文件夹来创建
    System.out.println(result);

  }

}
