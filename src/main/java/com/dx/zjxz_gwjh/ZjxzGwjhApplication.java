package com.dx.zjxz_gwjh;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dx.easyspringweb.api.EnableEasyApiModule;
import com.dx.easyspringweb.biz.EnableEasyBizModule;
import com.dx.easyspringweb.core.EnableEasyCoreModule;
import com.dx.easyspringweb.data.EnableEasyDataModule;
import com.dx.easyspringweb.resource.EnableResourceServer;
import com.dx.easyspringweb.rpc.EnableEasyRpcModule;
import com.dx.easyspringweb.storage.EnableStorageServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaRepositories
@EntityScan
@SpringBootApplication
@EnableEasyCoreModule
@EnableEasyApiModule
@EnableEasyRpcModule
@EnableEasyBizModule
@EnableEasyDataModule
@EnableResourceServer
@EnableStorageServer
public class ZjxzGwjhApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        SpringApplication.run(ZjxzGwjhApplication.class, args);
    }
}
