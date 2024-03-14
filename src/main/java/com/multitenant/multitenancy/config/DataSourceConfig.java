package com.multitenant.multitenancy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @Autowired
    @Qualifier("globalDatasource")
    private Map<Object, Object> dataSourceMap;

    @Bean
    @Primary
    public DataSource dataSource() {
        CustomRoutingDataSource customDataSource = new CustomRoutingDataSource();
        customDataSource.setTargetDataSources(dataSourceMap);
        return customDataSource;
    }
}
