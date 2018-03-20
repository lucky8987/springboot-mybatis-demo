package com.vincent.config;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

/**
 * 数据源-路由
 */
@Component
public class DataSourceRouting extends AbstractRoutingDataSource {

    @Autowired
    private Map<String, DataSource> targetDataSources;

    @Override
    public void afterPropertiesSet() {
        this.setTargetDataSources(Arrays.stream(DataSourceEnum.values()).collect(Collectors.toMap(k -> k.name, v -> targetDataSources.get(v.name))));
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getReadOrWrite();
    }

}
