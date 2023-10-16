package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.ActivityCreateDto;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import com.dx.zjxz_gwjh.filter.ActivityFilter;
import com.dx.zjxz_gwjh.service.ActivityService;
import com.dx.zjxz_gwjh.vo.ActivityDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Activity")
@Api(name = "ActivityManagement", description = "网格活动管理")
@RestController
@RequestMapping("/management/activity")
@BindResource(value = "activity:management", menu = false)
public class ActivityManagementController {

    @Autowired
    private ActivityService activityService;

    @BindResource("activity:management:list")
    @Action(value = "查询所有活动列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<ActivityDetailVO> getCurrActivityList(@RequestBody QueryRequest<ActivityFilter> query) throws ServiceException {
        if (query == null) query = QueryRequest.create(null);
        PagingData<ActivityEntity> result = activityService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, ActivityDetailVO.class));
    }

    @BindResource(value = "activity:management:create")
    @Action(value = "创建活动", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public ActivityEntity create(@Valid @RequestBody ActivityCreateDto dto) throws ServiceException {
        ActivityEntity entity = activityService.newEntity(dto);
        entity = activityService.create(entity);
        return entity;
    }

    @BindResource("activity:management:delete")
    @Action(value = "删除活动", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        activityService.deleteById(id);
    }

    @BindResource("activity:management:details")
    @Action(value = "查询活动详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public ActivityEntity details(@RequestParam("id") String id) throws ServiceException {
        return activityService.getById(id);
    }

    @BindResource("activity:management:update")
    @Action(value = "更新活动", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody ActivityEntity dto) throws ServiceException {
        ActivityEntity entity = activityService.getById(dto.getId());
        ObjectUtils.copyEntity(dto, entity);
        activityService.update(entity);
    }

}
