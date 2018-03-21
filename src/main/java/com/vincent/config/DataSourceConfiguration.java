package com.vincent.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.vincent.plugin.MyPlugin;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;


/**
 * mutil-dataSource 多数据源配置
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * 主库-write
     * @return
     */
    @Bean(DataSourceEnum.WRITE_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "datasource.primary")
    public DataSource numberMasterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 从库-read
     * @return
     */
    @Bean(DataSourceEnum.READ_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "datasource.secondary")
    public DataSource provisioningDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 使application.properties配置生效，如果不主动配置，由于@Order配置顺序不同，将导致配置不能及时生效
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration mybatisConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    /**
     * 设置sqlsessionFactory 中的 dataSource 从 数据源路由中获得
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceRouting") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfiguration(mybatisConfiguration());
        return sessionFactoryBean.getObject();
    }


    /**
     * 设置transactionManager 中的 dataSource 从 数据源路由中获得
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSourceRouting") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
