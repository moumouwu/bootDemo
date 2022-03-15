package com.pay.demo.controller;

import org.checkerframework.checker.units.qual.C;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigDecimal;

/**
 * @author Qin
 * @version 1.0
 * @date 2021-04-01 9:40
 */
public class TestMain {

  public static void main(String[] args) throws IOException {
//    ClassPathResource classPathResource = new ClassPathResource("static/apiclient_key.pem");
    ClassPathResource classPathResource = new ClassPathResource("static/private_key.txt");
    File file = classPathResource.getFile();
    String privateKey = getPrivateKey(file);
    System.out.println(privateKey);
  }
  public static String getPrivateKey( File file){
//    File file = new File(fileName);
    BufferedReader reader = null;
    StringBuffer sbf = new StringBuffer();
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempStr;
      while ((tempStr = reader.readLine()) != null) {
        sbf.append(tempStr);
      }
      reader.close();
      return sbf.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
    return sbf.toString();
  }
}
