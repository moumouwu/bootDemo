package com.zhty.inspect.controller;

import com.zhty.inspect.config.redis.RedisUtils;
import com.zhty.inspect.entity.R;
import com.zhty.inspect.service.AuthService;
import com.zhty.inspect.utils.RandomUtil;
import com.zhty.inspect.utils.RegValidUtil;
import java.security.Principal;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-17 10:39
 */
@RestController
@Slf4j
public class AuthController {

  @Resource
  private RedisUtils redisTemplate;

  @Autowired
  private AuthService authService;

  public final static String SMS_CODE_PREFIX = "sms-code:";

  /**
   * 根据手机号生成手机验证码
   * @param phone
   * @return
   */
  @GetMapping("/sms/get-code")
  public R generateCode(@RequestParam(required = false, defaultValue = "") String phone) {
    log.info("手机号：{}", phone);
    if ("".equals(phone)) {
      return R.error("手机号等于空");
    }
    Boolean validPhone = RegValidUtil.isValidPhone(phone);
    if (!validPhone) {
      return R.error("手机号不正确");
    }
    boolean exist = redisTemplate.hasKey(SMS_CODE_PREFIX+ phone);
    if (exist){
      return R.error("您发送的太频繁了");
    }
    String smsCode = RandomUtil.getNum(6);
    redisTemplate.set(SMS_CODE_PREFIX+phone, smsCode, 60);
    // 实现短信发送接口
    // ......
    return R.ok("查询成功");
  }

  @GetMapping("/auth-code")
  public R getCode(String code) {
    return R.ok(code);
  }

  @GetMapping("/thirty/oauth/github")
  public R getGithub(String code) {
    return R.ok(code);
  }


  @PreAuthorize("hasAuthority('wechat_user')")
  @GetMapping("/v1/get/1")
  public R get1() {
    return R.ok("微信普通用户");
  }

  @PreAuthorize("hasAuthority('client')")
  @GetMapping("/t1/get/2")
  public R get2() {
    return R.ok("微信绑定用户");
  }

  @GetMapping("/t1/get/3")
  public R get3(Principal principal) {
    return R.ok("获取用户信息成功", principal);
  }

}
