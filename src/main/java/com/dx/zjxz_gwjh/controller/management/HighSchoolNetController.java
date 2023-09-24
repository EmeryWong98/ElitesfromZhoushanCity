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
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolNetEntity;
import com.dx.zjxz_gwjh.filter.NetFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.HighSchoolNetService;
import com.dx.zjxz_gwjh.service.HighSchoolService;
import com.dx.zjxz_gwjh.vo.HighSchoolNetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Net")
@Api(name = "HighSchoolNetManagement", description = "高中网格管理")
@RestController
@RequestMapping("/management/net/highSchoolNet")
@BindResource(value = "highSchoolNet:management")
public class HighSchoolNetController {
    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @BindResource(value = "highSchoolNet:management:list")
    @Action(value = "查询高中网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<HighSchoolNetVO> list(@Session RDUserSession user, @RequestBody QueryRequest<NetFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<HighSchoolNetEntity> result = highSchoolNetService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, HighSchoolNetVO.class));
    }

    @BindResource(value = "highSchoolNet:management:create")
    @Action(value = "创建高中网格信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public HighSchoolNetEntity create(@Session RDUserSession user, @Valid @RequestBody NetNameDto dto)
            throws ServiceException {

        HighSchoolNetEntity entity = highSchoolNetService.newEntity(dto);

        entity = highSchoolNetService.create(entity);

        return entity;
    }

    @BindResource("highSchoolNet:management:delete")
    @Action(value = "删除高中网格", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        highSchoolNetService.deleteById(id);
    }

    @BindResource("highSchoolNet:management:details")
    @Action(value = "查询高中网格详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public HighSchoolNetEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return highSchoolNetService.getById(id);
    }

    @BindResource("highSchoolNet:management:update")
    @Action(value = "更新高中网格信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody NetNameDto dto) throws ServiceException {
        // 获取现有的高中实体
        HighSchoolNetEntity entity = highSchoolNetService.getById(dto.getId());

        // 将dto中的字段复制到现有的高中实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新高中实体
        highSchoolNetService.update(entity);
    }


}
