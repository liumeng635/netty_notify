package com.lty.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Description:   主数据源
 * @author: yuwenfeng
 * @date:   2018年1月17日 下午12:09:23   
*/

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.lty.dao.primary"}, sqlSessionFactoryRef = "sqlSessionFactorPrimary")
public class RepositoryPrimaryConfig {
    @Autowired
    @Qualifier("primaryDS")
    private DataSource primaryDS;

    /**
     * 加载默认mybatis xml配置文件，并初始化分页插件
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactorPrimary")
    public SqlSessionFactory sqlSessionFactory( ) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(primaryDS);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources( "classpath:mapper/**/*.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.lty.entity.primary");
        return sqlSessionFactoryBean.getObject();
    }


    @Bean(name = "transactionManagerPrimary")
    public PlatformTransactionManager transactionManager() throws SQLException {
        return new DataSourceTransactionManager(primaryDS);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        // 使用上面配置的Factory
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        return template;
    }
}
