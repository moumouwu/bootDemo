package com.zhty.inspect.config.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 对于某些类中无法使用@Autowire从spring容器中自动注入bean，因为不是它管理的
 *
 * 该工具类帮助我们直接中spring中获取bean
 *
 * 根据类实例名获取，例：
 *
 *  public StringRedisTemplate stringRedisTemplate = ApplicationContextAwareUtil.getBean("stringRedisTemplate");
 *
 * @author Qin
 * @version 1.0
 * @date 2020-12-17 10:13
 */
@Component
public class ApplicationContextAwareUtil implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ApplicationContextAwareUtil.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) throws BeansException {
    if (applicationContext == null) {
      return null;
    }
    return (T) applicationContext.getBean(name);
  }
}
