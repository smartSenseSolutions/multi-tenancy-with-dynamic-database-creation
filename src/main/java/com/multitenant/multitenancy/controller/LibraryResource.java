package com.multitenant.multitenancy.controller;

import com.multitenant.multitenancy.config.TenantContext;
import com.multitenant.multitenancy.config.TenantUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryResource {

    @PostMapping("/api/test/library")
    public void createLibrary() {
        TenantUtil.createDatabase(TenantContext.getCurrentTenant());
        TenantUtil.runLiquibase();
    }


}
