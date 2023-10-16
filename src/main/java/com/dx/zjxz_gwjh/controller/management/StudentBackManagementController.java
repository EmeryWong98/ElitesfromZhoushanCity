package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.StudentJourneyLogDto;
import com.dx.zjxz_gwjh.entity.ActivityEntity;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.filter.StudentJourneyLogEntityFilter;
import com.dx.zjxz_gwjh.service.StudentJourneyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Students")
@Api(name = "StudentsBackManagement", description = "学子工作记录管理")
@RestController
@RequestMapping("/management/studentBack")
@BindResource(value = "studentBack:management", menu = false)
public class StudentBackManagementController {

    @Autowired
    private StudentJourneyLogService studentJourneyLogService;

    @BindResource("studentBack:management:list")
    @Action(value = "查询所有学子工作记录", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<StudentJourneyLogEntity> getCurrActivityList(@RequestBody QueryRequest<StudentJourneyLogEntityFilter> query) throws ServiceException {
        if (query == null) query = QueryRequest.create(null);
        PagingData<StudentJourneyLogEntity> result = studentJourneyLogService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, StudentJourneyLogEntity.class));
    }

    @BindResource(value = "studentBack:management:create")
    @Action(value = "创建学子工作记录", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public StudentJourneyLogEntity create(@Valid @RequestBody StudentJourneyLogDto dto) throws ServiceException {
        StudentJourneyLogEntity entity = studentJourneyLogService.newEntity(dto);
        entity = studentJourneyLogService.create(entity);
        return entity;
    }

    @BindResource("studentBack:management:delete")
    @Action(value = "删除学子工作记录", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        studentJourneyLogService.deleteById(id);
    }

    @BindResource("studentBack:management:details")
    @Action(value = "查询学子工作记录详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentJourneyLogEntity details(@RequestParam("id") String id) throws ServiceException {
        return studentJourneyLogService.getById(id);
    }

    @BindResource("studentBack:management:update")
    @Action(value = "更新学子工作记录", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody ActivityEntity dto) throws ServiceException {
        StudentJourneyLogEntity entity = studentJourneyLogService.getById(dto.getId());
        ObjectUtils.copyEntity(dto, entity);
        studentJourneyLogService.update(entity);
    }

}
