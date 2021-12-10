package com.boot.tenant.config.druid;

import lombok.Data;

import java.io.Serializable;

/**
 * @author binSin
 * @date 2021/12/7
 */
@Data
public class RdsConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 数据库URL
     */
    private String dbUrl;

    /**
     * 数据库端口
     */
    private String dbPort;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库账号
     */
    private String dbAccount;

    /**
     * 数据库密码
     */
    private String dbPassword;
}
