package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.NetNameDto;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.AreaNetService;
import com.dx.zjxz_gwjh.vo.AreaNetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Net")
@Api(name = "AreaNetManagement", description = "属地网格管理")
@RestController
@RequestMapping("/management/net/areaNet")
@BindResource(value = "areaNet:management")
public class AreaNetManagementController {
    @Autowired
    private AreaNetService areaNetService;

    @BindResource(value = "areaNet:management:list")
    @Action(value = "查询属地网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<AreaNetVO> list(@Session RDUserSession user, @RequestBody QueryRequest<NetFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<AreaNetEntity> result = areaNetService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, AreaNetVO.class));
    }

    @BindResource(value = "areaNet:management:create")
    @Action(value = "创建属地网格信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public AreaNetEntity create(@Session RDUserSession user, @Valid @RequestBody NetNameDto dto)
            throws ServiceException {

        AreaNetEntity entity = areaNetService.newEntity(dto);

        entity = areaNetService.create(entity);

        return entity;
    }

    @BindResource("areaNet:management:delete")
    @Action(value = "删除属地网格", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        areaNetService.deleteById(id);
    }

    @BindResource("areaNet:management:details")
    @Action(value = "查询属地网格详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public AreaNetEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return areaNetService.getById(id);
    }

    @BindResource("areaNet:management:update")
    @Action(value = "更新属地网格信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody NetNameDto dto) throws ServiceException {

        AreaNetEntity entity = areaNetService.getById(dto.getId());

        ObjectUtils.copyEntity(dto, entity);

        areaNetService.update(entity);
    }


}
