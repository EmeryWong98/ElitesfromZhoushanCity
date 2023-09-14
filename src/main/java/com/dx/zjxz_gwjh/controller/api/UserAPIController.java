package com.dx.zjxz_gwjh.controller.api;

import org.springframework.web.bind.annotation.RestController;

import com.dx.zjxz_gwjh.dto.UserDto;
import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.zjxz_gwjh.filter.UserFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.UserService;
import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.biz.controller.api.BaseUserAPIController;

@RestController
@Api(name = "UserApi", description = "用户API")
public class UserAPIController
        extends BaseUserAPIController<RDUserSession, UserService, UserEntity, UserDto, UserFilter, String> {

    public UserAPIController(UserService service) {
        super(service);
    }

}
