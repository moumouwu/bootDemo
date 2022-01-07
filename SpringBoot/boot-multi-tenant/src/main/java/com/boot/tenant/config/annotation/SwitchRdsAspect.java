//package com.boot.tenant.config.annotation;
//
//import com.boot.tenant.config.annotation.SwitchRds;
//import com.boot.tenant.service.TenantRdsService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author binSin
// * @date 2021/12/7
// */
//@Aspect
//@Component
//@Slf4j
//public class SwitchRdsAspect {
//    /**
//     * 租户rds服务类
//     */
//    @Autowired
//    private TenantRdsService tenantRdsServiceImpl;
//
//    /**
//     * 切点
//     * 连接点：直接指定为注解
//     * 注意：com.xxx.SwitchRds这里包名自行修改
//     *
//     * @date 2021/8/27 14:26
//     **/
//    @Pointcut("@annotation(com.boot.tenant.config.annotation.SwitchRds)")
//    public void myPointcut() {
//    }
//
//    /**
//     * 环绕通知
//     *
//     * @return java.lang.Object
//     * @date 2021/8/27 14:26
//     **/
//    @Around(value = "myPointcut()")
//    public Object around(ProceedingJoinPoint pjp) throws Throwable {
//        SwitchRds annotation = getAnnotation(pjp);
//        // 获取注解上的租户代码
//        String tenantCode = annotation.tenantCode();
//        String dataSource = annotation.dataSource();
//        System.out.println(tenantCode + ":::" + dataSource);
//        Object proceed;
//        try {
//            if (StringUtils.isNotBlank(dataSource)) {
//                tenantRdsServiceImpl.switchRdsByDataSourceName(dataSource);
//            } else if (StringUtils.isNotBlank(tenantCode)) {
//                tenantRdsServiceImpl.switchRds(tenantCode);
//            } else {
//                throw new RuntimeException("出错了");
//            }
//            // 执行
//            proceed = pjp.proceed();
//        } finally {
//            // todo 这里需要做移除切换的数据源也可以，但是如果没移除再下次切换的时候会先切换到配置库
//        }
//        return proceed;
//    }
//
//    /**
//     * 获取注解
//     *
//     * @param pjp
//     * @date 2021/8/27 17:58
//     **/
//    private SwitchRds getAnnotation(ProceedingJoinPoint pjp) {
//        // 尝试获取类上的注解
//        SwitchRds annotation = pjp.getTarget().getClass().getAnnotation(SwitchRds.class);
//        // 如果类上没有注解则获取方法上面的
//        if (null == annotation) {
//            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
//            annotation = methodSignature.getMethod().getAnnotation(SwitchRds.class);
//        }
//        return annotation;
//    }
//
//}
