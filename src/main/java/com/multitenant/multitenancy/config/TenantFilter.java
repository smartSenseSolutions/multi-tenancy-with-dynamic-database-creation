package com.multitenant.multitenancy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Component
@Order(1)
@Slf4j
class TenantFilter implements Filter {
    @Autowired
    @Qualifier("globalDatasource")
    private Map<Object, Object> dataSourceMap;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String tenantName = req.getHeader("X-TenantID");
        TenantContext.setCurrentTenant(tenantName);
        if (!dataSourceMap.containsKey(tenantName)) {
            dataSourceMap.put(tenantName, TenantUtil.getTenantDataSource());
            TenantUtil.runLiquibase();
        }
        chain.doFilter(request, response);
    }
}
