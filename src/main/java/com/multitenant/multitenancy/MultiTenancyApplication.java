package com.multitenant.multitenancy;

import com.multitenant.multitenancy.config.TenantUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
public class MultiTenancyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenancyApplication.class, args);
    }

    @Bean
    @Qualifier("globalDatasource")
    public Map<Object, Object> getDataSource() {
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", TenantUtil.getMasterDataSource());
        return dataSourceMap;
    }
}
