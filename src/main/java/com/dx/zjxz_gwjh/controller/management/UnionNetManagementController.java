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
import com.dx.zjxz_gwjh.dto.UnionNetDto;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.entity.UnionNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.UnionNetService;
import com.dx.zjxz_gwjh.vo.UnionNetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("Net")
@Api(name = "UnionNetManagement", description = "学联网格管理")
@RestController
@RequestMapping("/management/net/unionNet")
@BindResource(value = "unionNet:management")
public class UnionNetManagementController {
    @Autowired
    private UnionNetService unionNetService;

    @BindResource(value = "unionNet:management:list")
    @Action(value = "查询学联网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<UnionNetVO> list(@Session RDUserSession user, @RequestBody QueryRequest<NetFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<UnionNetEntity> result = unionNetService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, UnionNetVO.class));
    }

    @BindResource(value = "unionNet:management:create")
    @Action(value = "创建学联网格信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public UnionNetEntity create(@Session RDUserSession user, @Valid @RequestBody UnionNetDto dto)
            throws ServiceException {

        UnionNetEntity entity = unionNetService.newEntity(dto);

        entity = unionNetService.create(entity);

        return entity;
    }

    @BindResource("unionNet:management:delete")
    @Action(value = "删除学联网格", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        unionNetService.deleteById(id);
    }

    @BindResource("unionNet:management:details")
    @Action(value = "查询学联网格详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public UnionNetEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return unionNetService.getById(id);
    }

    @BindResource("unionNet:management:update")
    @Action(value = "更新学联网格信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody UnionNetDto dto) throws ServiceException {

        UnionNetEntity entity = unionNetService.getById(dto.getId());

        ObjectUtils.copyEntity(dto, entity);

        unionNetService.update(entity);
    }

    @BindResource("unionNet:management:list")
    @Action(value = "查询学联网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/lists")
    public List<UnionNetEntity> lists(){
        return unionNetService.lists();
    }
}
