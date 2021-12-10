package com.boot.tenant.constant;

/**
 * @author binSin
 * @date 2021/12/7
 */
public class DataSourceConstant {
    private DataSourceConstant() {
    }

    /**
     * 这里的命名统一在配置文件命名的基础上加dataSource前缀且改小驼峰
     * 默认数据源名称
     */
    public static final String DATA_SOURCE_MASTER = "dataSourceMaster";

    /**
     * 递增可配数据源名称
     * 这里的命名统一在配置文件命名的基础上加dataSource前缀且改小驼峰
     * 后面可接着 db2... dbn 也可以根据
     */
//    public static final String DATA_SOURCE_DB_1 = "dataSourceDb1";
}
