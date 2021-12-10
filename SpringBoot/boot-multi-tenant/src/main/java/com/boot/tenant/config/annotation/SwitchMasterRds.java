package com.boot.tenant.config.annotation;

/**
 * @author binSin
 * @date 2021/12/7
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切换至主数据源-自定义注解
 * 这个仅为了方便使用，用SwitchRds注解指定为默认数据源也可以实现
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchMasterRds {
}
