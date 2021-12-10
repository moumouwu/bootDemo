package com.boot.tenant.config.druid;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.boot.tenant.constant.DataSourceConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * @author binSin
 * @date 2021/12/7
 */
@Configuration
@MapperScan(value = {"com.boot.tenant.mapper"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // 排除 DataSourceAutoConfiguration 的自动配置，避免环形调用
public class DataSourceConfig {
    /**
     * 默认数据源
     *
     * @return
     */
    @Bean(DataSourceConstant.DATA_SOURCE_MASTER)
    @ConfigurationProperties("spring.datasource.druid.master")
    public DataSource dataSourceMaster() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 递增数据源
     *
     * @return
     */
//    @Bean(DataSourceConstant.DATA_SOURCE_DB_1)
//    @ConfigurationProperties("spring.datasource.druid.db1")
//    public DataSource dataSourceDb1() {
//        return DruidDataSourceBuilder.create().build();
//    }


    /**
     * 设置动态数据源为主数据源
     *
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource() {
        // 将数据源设置进map
        DynamicDataSource.setDataSourceMap(DataSourceConstant.DATA_SOURCE_MASTER, dataSourceMaster());
//        DynamicDataSource.setDataSourceMap(DataSourceConstant.DATA_SOURCE_DB_1, dataSourceDb1());
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 使用 Map 保存多个数据源，并设置到动态数据源对象中，这个值最终会在afterPropertiesSet中被设置到resolvedDataSources上
        dynamicDataSource.setTargetDataSources(DynamicDataSource.dataSourceMap);
        return dynamicDataSource;
    }
}
