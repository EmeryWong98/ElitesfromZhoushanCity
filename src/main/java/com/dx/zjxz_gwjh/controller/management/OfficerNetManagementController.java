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
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.entity.OfficerNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.OfficerNetService;
import com.dx.zjxz_gwjh.service.UnionNetService;
import com.dx.zjxz_gwjh.vo.OfficerNetVO;
import com.dx.zjxz_gwjh.vo.OfficerNetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("Net")
@Api(name = "OfficerNetManagement", description = "党政领导管理")
@RestController
@RequestMapping("/management/net/officerNet")
@BindResource(value = "officerNet:management")
public class OfficerNetManagementController {
    @Autowired
    private OfficerNetService officerNetService;

    @BindResource(value = "officerNet:management:list")
    @Action(value = "查询党政领导列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<OfficerNetVO> list(@Session RDUserSession user, @RequestBody QueryRequest<NetFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<OfficerNetEntity> result = officerNetService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, OfficerNetVO.class));
    }

    @BindResource(value = "officerNet:management:create")
    @Action(value = "创建党政领导网格信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public OfficerNetEntity create(@Session RDUserSession user, @Valid @RequestBody NetNameDto dto)
            throws ServiceException {

        OfficerNetEntity entity = officerNetService.newEntity(dto);

        entity = officerNetService.create(entity);

        return entity;
    }

    @BindResource("officerNet:management:delete")
    @Action(value = "删除党政领导网格", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        officerNetService.deleteById(id);
    }

    @BindResource("officerNet:management:details")
    @Action(value = "查询党政领导网格详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public OfficerNetEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return officerNetService.getById(id);
    }

    @BindResource("officerNet:management:update")
    @Action(value = "更新党政领导网格信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody NetNameDto dto) throws ServiceException {

        OfficerNetEntity entity = officerNetService.getById(dto.getId());

        ObjectUtils.copyEntity(dto, entity);

        officerNetService.update(entity);
    }

    @BindResource("officerNet:management:list")
    @Action(value = "查询党政领导网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/lists")
    public List<OfficerNetEntity> lists(){
        return officerNetService.lists();
    }
}
