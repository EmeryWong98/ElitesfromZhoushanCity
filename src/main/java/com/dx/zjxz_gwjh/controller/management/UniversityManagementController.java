package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.UniversityDto;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.UniversityService;
import com.dx.zjxz_gwjh.vo.UniversityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dx.easyspringweb.core.model.PagingData;

@ApiModule("University")
@Api(name = "UniversityManagement", description = "大学管理")
@RestController
@RequestMapping("/management/university")
@BindResource(value = "university:management")
public class UniversityManagementController {
    @Autowired
    private UniversityService universityService;

    @BindResource(value = "university:management:list")
    @Action(value = "查询大学列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<UniversityVO> list(@Session RDUserSession user, @RequestBody QueryRequest<UniversityFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<UniversityEntity> result = universityService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, UniversityVO.class));
    }

    @BindResource(value = "university:management:create")
    @Action(value = "创建大学信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public UniversityEntity create(@Session RDUserSession user, @RequestBody UniversityDto dto)
            throws ServiceException {

        UniversityEntity entity = universityService.newEntity(dto);

        entity = universityService.create(entity);

        return entity;
    }

    @BindResource("university:management:delete")
    @Action(value = "删除大学", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id)
            throws ServiceException {

        universityService.deleteById(id);
    }

    @BindResource(value = "university:management:details")
    @Action(value = "查询大学详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public UniversityEntity details(@RequestParam("id") String id)
            throws ServiceException {

        return universityService.getById(id);
    }

    @BindResource(value = "university:management:update")
    @Action(value = "更新大学信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@RequestBody UniversityDto dto)
            throws ServiceException {

        UniversityEntity entity = universityService.getById(dto.getId());

        ObjectUtils.copyEntity(dto, entity);

        universityService.update(entity);
    }

}
