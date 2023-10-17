package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.WeChatNetDetailsDto;
import com.dx.zjxz_gwjh.dto.WeChatStudentsDetailsDto;
import com.dx.zjxz_gwjh.service.WeChatService;
import com.dx.zjxz_gwjh.vo.WeChatNetDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiModule("WeChat")
@Api(name = "WeChatApi", description = "微信端接口")
@RestController
@RequestMapping("/api/wechat")
@BindResource(value = "wechat:api")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;

    @BindResource("net:api:wgydetails")
    @Action(value = "网格员详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/wgydetails")
    public WeChatNetDetailsVO wgydetails(@RequestParam("userId") String userId) throws ServiceException {
        WeChatNetDetailsDto dto = weChatService.getNetDetailsByUserId(userId);
        if (dto == null) {
            throw new ServiceException("没有找到该网格员的详情");
        }

        WeChatNetDetailsVO vo = new WeChatNetDetailsVO();

        vo.setUserId(dto.getUserId());
        vo.setUserName(dto.getUserName());
        vo.setTotal(dto.getTotal());

        return vo;
    }

    @BindResource("net:api:studentsdetails")
    @Action(value = "网格学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/studentsdetails")
    public List<WeChatStudentsDetailsDto> getStudentsDetails(@RequestParam("userId") String userId) throws ServiceException {
        return weChatService.getStudentsByUserId(userId);
    }

}
