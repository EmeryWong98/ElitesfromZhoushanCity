package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.biz.controller.api.BaseUserAPIController;
import com.dx.easyspringweb.biz.vo.LoginUserInfo;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.PermitAll;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.UserDto;
import com.dx.zjxz_gwjh.entity.UserEntity;
import com.dx.zjxz_gwjh.filter.UserFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiModule("WeChat")
@RequestMapping("/auth")
@BindResource(value = "wechat:api:auth")
@RestController
public class AuthController extends BaseUserAPIController<RDUserSession, UserService, UserEntity, UserDto, UserFilter, String> {

    @Autowired
    private UserService userService;

    public AuthController(UserService service) {
        super(service);
    }

    @PermitAll
    @Action("微信免登")
    @PostMapping("/wx/login")
    public LoginUserInfo<RDUserSession> getAuthUrl(@RequestParam("unionId") String unionId) throws ServiceException {
        UserEntity entity = userService.getByWechatId(unionId);
        if(entity == null){
            entity = new UserEntity();
            entity.setWechatId(unionId);
            entity.setUserName("测试名称");
        }
        String token = getOauthService().getAccessToken(entity.getUserName());
        RDUserSession session = userService.createSession(entity);
        getSessionManager().save(session);
        return new LoginUserInfo<>(token, session);
    }
}
