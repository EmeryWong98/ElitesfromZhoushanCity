package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.ActivityCreateDto;
import com.dx.zjxz_gwjh.dto.ActivityStudentQueryDto;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import com.dx.zjxz_gwjh.filter.ActivityFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.ActivityService;
import com.dx.zjxz_gwjh.vo.ActivityDetailVO;
import com.dx.zjxz_gwjh.vo.ActivityStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("Activity")
@Api(name = "ActivityWxApi", description = "网格活动微信API")
@RestController
@RequestMapping("/wx/activity")
@BindResource(value = "activity:wx", menu = false)
public class ActivityWxController {
    @Autowired
    private ActivityService activityService;

    @BindResource("activity:wx:list")
    @Action(value = "网格活动列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<ActivityEntity> queryList(@Session RDUserSession user, @RequestBody QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        filter.setUserId(user.getUserId());
        return activityService.queryList(query);
    }

    @BindResource("activity:wx:detail")
    @Action(value = "网格活动详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/detail")
    public ActivityDetailVO queryItem(@RequestParam("id") String id) throws ServiceException {
        return activityService.getDetail(id);
    }

    @BindResource("activity:wx:curr-list")
    @Action(value = "进行网格活动列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/currList")
    public PagingData<ActivityDetailVO> queryCurrList(@Session RDUserSession user, @RequestBody QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        filter.setUserId(user.getUserId());
        return activityService.getCurrActivityList(query);
    }

    @BindResource("activity:wx:wait-list")
    @Action(value = "计划网格活动列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/waitList")
    public PagingData<ActivityDetailVO> queryWaitList(@Session RDUserSession user, @RequestBody QueryRequest<ActivityFilter> query) throws ServiceException {
        ActivityFilter filter = query.getFilter();
        filter.setUserId(user.getUserId());
        return activityService.getWaitActivityList(query);
    }

    @BindResource("activity:wx:netStudent")
    @Action(value = "网格学生列表")
    @PostMapping("/netStudent")
    public List<ActivityStudentVO> queryStudentByNetUserId(@Session RDUserSession user, @RequestBody ActivityStudentQueryDto activityStudentQueryDto) throws ServiceException {
        if (activityStudentQueryDto.getUserId() == null) {
            activityStudentQueryDto.setUserId(user.getUserId());
        }
        return activityService.queryStudentByNetUserId(activityStudentQueryDto);
    }

    @BindResource("activity:wx:create")
    @Action(value = "创建网格活动", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public ActivityEntity create(@Session RDUserSession user, @Valid @RequestBody ActivityCreateDto dto) throws ServiceException {
        if (dto.getUserId() == null) {
            dto.setUserId(user.getUserId());
        }
        ActivityEntity entity = activityService.newEntity(dto);
        return activityService.create(entity);
    }

    @BindResource("activity:api:update")
    @Action(value = "更新网格活动", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public ActivityEntity update(@Session RDUserSession user, @Valid @RequestBody ActivityCreateDto dto) throws ServiceException {
        if (dto.getUserId() == null) {
            dto.setUserId(user.getUserId());
        }
        ActivityEntity entity = activityService.getById(dto.getId());
        ObjectUtils.copyEntity(dto, entity);
        return activityService.update(entity);
    }

    @BindResource("activity:api:delete")
    @Action(value = "删除网格活动", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam String id) throws ServiceException {
        activityService.deleteById(id);
    }
}
