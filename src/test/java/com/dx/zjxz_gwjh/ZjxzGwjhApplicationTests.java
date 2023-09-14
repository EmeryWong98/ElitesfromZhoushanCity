package com.dx.zjxz_gwjh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dx.easyspringweb.biz.zwding.service.ZWDingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class ZjxzGwjhApplicationTests {
    // @Autowired
    // private UserService userService;

    // @Autowired
    // private ObjectMapper mapper;

    // @Autowired
    // private SubMailService mailService;

    // @Autowired
    // private ApplyRecordService service;

    @Autowired
    private ZWDingService zwdingService;

    @Test
    void contextLoads() throws Exception {

    }

}
