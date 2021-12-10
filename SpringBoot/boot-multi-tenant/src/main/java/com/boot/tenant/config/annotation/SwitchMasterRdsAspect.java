package com.boot.tenant.config.annotation;

import com.boot.tenant.constant.DataSourceConstant;
import com.boot.tenant.service.TenantRdsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author binSin
 * @date 2021/12/7
 */
@Aspect
@Component
@Slf4j
public class SwitchMasterRdsAspect {
    /**
     * 租户rds服务类
     */
    @Autowired
    private TenantRdsService tenantRdsServiceImpl;

    /**
     * 切点
     * 连接点：直接指定为注解
     * 注意：com.xxx.SwitchMasterRds这里包名自行修改
     * @date 2021/8/27 14:26
     **/
    @Pointcut("@annotation(com.boot.tenant.config.annotation.SwitchMasterRds)")
    public void myPointcut() {
    }

    /**
     * 环绕通知
     *
     * @return java.lang.Object
     * @date 2021/8/27 14:26
     **/
    @Around(value = "myPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed;
        try {
            tenantRdsServiceImpl.switchRdsByDataSourceName(DataSourceConstant.DATA_SOURCE_MASTER);
            // 执行
            proceed = pjp.proceed();
        } finally {
            // todo 这里需要做移除切换的数据源也可以，但是如果没移除再下次切换的时候会先切换到配置库
        }
        return proceed;
    }
}
