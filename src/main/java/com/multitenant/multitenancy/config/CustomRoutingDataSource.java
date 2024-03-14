package com.multitenant.multitenancy.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        afterPropertiesSet();
        String currentTenant = TenantContext.getCurrentTenant();
        if (StringUtils.isEmpty(currentTenant)) {
            return "master";
        }
        return currentTenant;
    }
}