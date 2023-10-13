package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.biz.model.User;
import com.dx.easyspringweb.biz.vo.LoginUserInfo;
import com.dx.easyspringweb.biz.zwding.model.DingtalkAppUser;
import com.dx.easyspringweb.core.annotation.PermitAll;
import com.dx.easyspringweb.core.exception.APIException;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.rpc.account.service.OAuthAccountService;
import com.dx.easyspringweb.security.UserSessionManager;
import com.dx.easyspringweb.security.model.TokenData;
import com.dx.zjxz_gwjh.model.RDUserSession;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dx.zjxz_gwjh.dto.UserDto;
import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.zjxz_gwjh.filter.UserFilter;
import com.dx.zjxz_gwjh.service.UserService;
import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.biz.controller.api.BaseUserAPIController;
import com.dx.easyspringweb.core.annotation.Action;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(name = "UserApi", description = "用户API")
public class UserAPIController
        extends BaseUserAPIController<RDUserSession, UserService, UserEntity, UserDto, UserFilter, String> {

    @Autowired
    private UserService userService;

    public UserAPIController(UserService service) {
        super(service);
    }


    @PermitAll
    @Action("浙里办免登")
    @PostMapping("/zlb/login")
    public LoginUserInfo<RDUserSession> loginByZLBCode(@RequestParam("code") String code) throws ServiceException {
        if (!code.equals("zlbmiandeng")) {
            throw new APIException(-20001, "口令错误");
        }

        try {
            // Assuming userService can directly get the user entity based on the code
            UserEntity entity = userService.getByUserName("zlbmiandeng");
            if (entity == null) {
                throw new APIException(-20003, "根据Code[" + code + "]未找到用户");
            }

            String token = getOauthService().getAccessToken(entity.getUserName());
            if (token == null) {
                throw new APIException(-20004, "获取用户Token失败");
            }

            RDUserSession session = userService.createSession(entity);
            getSessionManager().save(session);
            return new LoginUserInfo<>(token, session);
        } catch (Exception e) {
            e.printStackTrace();
            throw new APIException(-20005, "浙里办API异常:" + e.getMessage());
        }
    }



}
