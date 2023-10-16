package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.enums.NetType;
import com.dx.zjxz_gwjh.service.ActivityService;
import com.dx.zjxz_gwjh.vo.ActivityDetailVO;
import com.dx.zjxz_gwjh.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiModule("Activity")
@Api(name = "ActivityApi", description = "网格活动API")
@RestController
@RequestMapping("/api/activity")
@BindResource(value = "activity:api", menu = false)
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @BindResource("activity:api:curr-list")
    @Action("查询正在执行的活动列表")
    @PostMapping("/currList")
    public List<ActivityVO> getCurrActivityList(@RequestParam("netType") NetType netType) throws ServiceException {
        return activityService.getCurrActivityList(netType);
    }

    @BindResource("activity:api:wait-list")
    @Action("查询还未执行的活动列表")
    @PostMapping("/waitList")
    public List<ActivityVO> getWaitActivityList(@RequestParam("netType") NetType netType) throws ServiceException {
        return activityService.getWaitActivityList(netType);
    }

    @BindResource("activity:api:detail")
    @Action("查询活动详情")
    @PostMapping("/detail")
    public ActivityDetailVO getDetail(@RequestParam("id") String id) throws ServiceException {
        return activityService.getDetail(id);
    }
}
