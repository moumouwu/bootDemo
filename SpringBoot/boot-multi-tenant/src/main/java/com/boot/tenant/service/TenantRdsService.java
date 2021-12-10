package com.boot.tenant.service;

import com.boot.tenant.config.druid.RdsConfig;

/**
 * @author binSin
 * @date 2021/12/7
 */
public interface TenantRdsService {

    /**
     * 获取rds配置
     *
     * @param tenantCode
     * @date 2021/8/28 13:53
     **/
    RdsConfig getRdsConfig(String tenantCode);

    /**
     * 根据租户代码切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param tenantCode
     * @date 2021/8/28 13:16
     **/
    void switchRds(String tenantCode);

    /**
     * 根据数据源名称切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param dataSourceName
     * @date 2021/8/28 13:16
     **/
    void switchRdsByDataSourceName(String dataSourceName);
}
