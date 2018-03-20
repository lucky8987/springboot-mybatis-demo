package com.vincent.config;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


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
     * 设置sqlsessionFactory 中的 dataSource 从 数据源路由中获得
     * @param dataSource
     * @return
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceRouting") DataSource dataSource) {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        try {
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            return null;
        }
    }

}
