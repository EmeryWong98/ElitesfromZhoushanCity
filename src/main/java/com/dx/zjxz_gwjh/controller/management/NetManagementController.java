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
import com.dx.zjxz_gwjh.dto.HighSchoolDto;
import com.dx.zjxz_gwjh.dto.NetNameDto;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.NetEntity;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.HighSchoolService;
import com.dx.zjxz_gwjh.service.NetService;
import com.dx.zjxz_gwjh.vo.HighSchoolVO;
import com.dx.zjxz_gwjh.vo.NetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Net")
@Api(name = "NetManagement", description = "网格管理")
@RestController
@RequestMapping("/management/net")
@BindResource(value = "net:management")
public class NetManagementController {
    @Autowired
    private NetService netService;

    @BindResource(value = "net:management:list")
    @Action(value = "查询网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<NetVO> list(@Session RDUserSession user, @RequestBody QueryRequest<NetFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<NetEntity> result = netService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, NetVO.class));
    }

    @BindResource(value = "net:management:create")
    @Action(value = "创建网格信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public NetEntity create(@Session RDUserSession user, @Valid @RequestBody NetNameDto dto)
            throws ServiceException {

        NetEntity entity = netService.newEntity(dto);

        entity = netService.create(entity);

        return entity;
    }

    @BindResource("net:management:delete")
    @Action(value = "删除网格", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        netService.deleteById(id);
    }

    @BindResource("net:management:details")
    @Action(value = "查询网格详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public NetEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return netService.getById(id);
    }

    @BindResource("net:management:update")
    @Action(value = "更新网格信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody NetNameDto dto) throws ServiceException {
        // 获取现有的高中实体
        NetEntity entity = netService.getById(dto.getId());

        // 将dto中的字段复制到现有的高中实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新高中实体
        netService.update(entity);
    }


}
