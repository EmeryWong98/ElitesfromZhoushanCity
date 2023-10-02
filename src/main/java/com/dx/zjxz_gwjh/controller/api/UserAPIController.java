package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.biz.vo.LoginUserInfo;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.exception.APIException;
import com.dx.easyspringweb.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    private UserService userService;

    public UserAPIController(UserService service) {
        super(service);
    }

//    @Action("用户免登")
//    @PostMapping("/user/login")
//    public LoginUserInfo<RDUserSession> loginByUserCode() throws ServiceException {
//        String code = "固定的Code";
//
//        if (userService == null) {
//            throw new APIException(-10001, "未开启用户登录");
//        }
//
//        try {
//            AppUser user = userService.getAccountInfo(code);
//            if (user == null) {
//                throw new APIException(-10002, "获取用户ID失败");
//            }
//
//            String accountId = user.getAccountId().toString();
//            TEntity entity = service.getByUserId(accountId);
//            if (entity == null) {
//                throw new APIException(-10003, "用户ID[" + accountId + "]未绑定");
//            }
//
//            String token = oauthService.getAccessToken(entity.getUserName());
//            if (token == null) {
//                throw new APIException(-10004, "获取用户Token失败");
//            }
//
//            TUserSession session = service.createSession(entity);
//            sessionManager.save(session);
//
//            return new LoginUserInfo<>(token, session);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new APIException(-10005, "用户API异常:" + e.getMessage());
//        }
//    }


}
