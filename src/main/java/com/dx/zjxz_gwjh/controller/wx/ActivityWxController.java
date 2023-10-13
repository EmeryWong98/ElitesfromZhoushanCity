package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.zjxz_gwjh.dto.ActivityCreateDto;
import com.dx.zjxz_gwjh.dto.ActivityStudentQueryDto;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import com.dx.zjxz_gwjh.filter.ActivityFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.ActivityService;
import com.dx.zjxz_gwjh.vo.ActivityStudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Action(value = "查询网格活动列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<ActivityEntity> queryList(@Session RDUserSession user, @RequestBody QueryRequest<ActivityFilter> query) throws ServiceException {
        return activityService.queryList(query);
    }

    @BindResource("activity:wx:netBindStudent")
    @Action(value = "根据网格员ID或者网格ID查询学生列表")
    @PostMapping("/netBindStudent")
    public List<ActivityStudentVO> queryStudentByNetUserId(@Session RDUserSession user, @RequestBody ActivityStudentQueryDto activityStudentQueryDto) throws ServiceException {
        return activityService.queryStudentByNetUserId(activityStudentQueryDto);
    }

    @BindResource("activity:wx:create")
    @Action(value = "创建网格活动", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public ActivityEntity create(@Session RDUserSession user, @Valid @RequestBody ActivityCreateDto dto) throws ServiceException {
        return activityService.create(dto);
    }

    @BindResource("activity:api:update")
    @Action(value = "更新网格活动", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public ActivityEntity update(@Session RDUserSession user, @Valid @RequestBody ActivityEntity entity) throws ServiceException {
        return activityService.update(entity);
    }

    @BindResource("activity:api:delete")
    @Action(value = "删除网格活动", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@Session RDUserSession user, @RequestBody String id) throws ServiceException {
        activityService.deleteById(id);
    }
}
