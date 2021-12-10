package com.boot.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.tenant.config.druid.DynamicDataSource;
import com.boot.tenant.config.druid.RdsConfig;
import com.boot.tenant.constant.DataSourceConstant;
import com.boot.tenant.mapper.RdsMapper;
import com.boot.tenant.mapper.TenantMapper;
import com.boot.tenant.pojo.Rds;
import com.boot.tenant.pojo.Tenant;
import com.boot.tenant.service.TenantRdsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author binSin
 * @date 2021/12/7
 */
@Slf4j
@Service
public class TenantRdsServiceImpl implements TenantRdsService {

    @Autowired
    private TenantMapper tenantMapper;
    //
    @Autowired
    private RdsMapper rdsMapper;

    /**
     * 获取rds配置
     *
     * @param tenantCode
     * @date 2021/8/28 13:53
     **/
    @Override
    public RdsConfig getRdsConfig(String tenantCode) {
        // 根据租户代码取租户表
        Tenant tenant = tenantMapper.selectOne(new QueryWrapper<Tenant>().eq("tenant_code", tenantCode));
        if (null == tenant) {
            return null;
        }

        // 取rds表
        Rds rds = rdsMapper.selectOne(new QueryWrapper<Rds>().eq("tenant_id", tenant.getId()));
        if (null == rds) {
            return null;
        }
        // 转换为rds配置
        RdsConfig rdsConfig = new RdsConfig();
        rdsConfig.setDbUrl(rds.getDbUrl());
        rdsConfig.setTenantCode(tenantCode);
        rdsConfig.setDbName(rds.getDbName());
        rdsConfig.setDbAccount(rds.getDbAccount());
        rdsConfig.setDbPassword(rds.getDbPwd());
        rdsConfig.setDbPort(String.valueOf(rds.getDbPort()));
        return rdsConfig;
    }

    /**
     * 根据租户代码切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param tenantCode
     * @date 2021/8/28 13:16
     **/
    @Override
    public void switchRds(String tenantCode) {

        if (StringUtils.isBlank(tenantCode)) {
            throw new RuntimeException();
        }
        // 如果当前已是这个租户rds则直接返回
        if (tenantCode.equals(DynamicDataSource.getDataSourceKey())) {
            return;
        }
        // 如果本地已有则不查了 改rds需要重启服务
        if (null == DynamicDataSource.getDataSourceMap(tenantCode)) {
            // 如果当前不是配置库则先切回配置库
            if (!DataSourceConstant.DATA_SOURCE_MASTER.equals(DynamicDataSource.getDataSourceKey())) {
                System.out.println("默认数据库");
                DynamicDataSource.setDataSourceDefault();
            }
            // 获取rds配置
            RdsConfig rdsConfig = getRdsConfig(tenantCode);
            if (null == rdsConfig) {
                throw new RuntimeException();
            }
            System.out.println("其他数据库");
            DynamicDataSource.setDataSourceMap(rdsConfig);
        }
        // 切换到业务库
        System.out.println("业务数据库");
        System.out.println(DynamicDataSource.getDataSourceKey());
        DynamicDataSource.setDataSource(tenantCode);
        System.out.println(DynamicDataSource.getDataSourceKey());
    }

    /**
     * 根据数据源名称切换rds连接，同一个线程内rds配置只会查一次
     *
     * @param dataSourceName
     * @date 2021/8/28 13:16
     **/
    @Override
    public void switchRdsByDataSourceName(String dataSourceName) {
        if (StringUtils.isBlank(dataSourceName)) {
            throw new RuntimeException();
        }
        // 如果当前已是这个数据源直接返回
        if (dataSourceName.equals(DynamicDataSource.getDataSourceKey())) {
            return;
        }
        // 如果本地已有则不查了 改rds需要重启服务
        if (null == DynamicDataSource.getDataSourceMap(dataSourceName)) {
            throw new RuntimeException();
        }
        // 切换
        DynamicDataSource.setDataSource(dataSourceName);
    }
}
