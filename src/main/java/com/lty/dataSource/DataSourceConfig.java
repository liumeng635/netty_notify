package com.lty.dataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**  
 * @Description:    数据源
 * @author: yuwenfeng
 * @date:   2018年1月17日 下午12:08:11   
*/
@Configuration
public class DataSourceConfig {
    @Bean(name = "primaryDS")
    @Qualifier("primaryDS")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
//        return DataSourceBuilder.create().build();
    }
}
