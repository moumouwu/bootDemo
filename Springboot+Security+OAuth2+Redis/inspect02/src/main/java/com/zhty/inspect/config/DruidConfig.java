package com.zhty.inspect.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-15 17:31
 */
@Configuration
@MapperScan(basePackages = "com.zhty.inspect.mapper", sqlSessionFactoryRef = "mysqlSessionFactory")
public class DruidConfig {

  @Bean(name = "mysqlDataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource druid(){
    return new DruidDataSource();
  }

  @Bean(name = "mysqlTransactionManager")
  public DataSourceTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean("mysqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception{
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setTypeAliasesPackage("com.zhty.inspect.entity");
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
    return sqlSessionFactoryBean.getObject();
  }

  @Bean(name = "sqlSessionTemplate")
  public SqlSessionTemplate sqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory){
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
