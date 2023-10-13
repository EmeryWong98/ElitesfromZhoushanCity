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
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.HighSchoolService;
import com.dx.zjxz_gwjh.vo.HighSchoolVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("HighSchool")
@Api(name = "HighSchoolManagement", description = "高中管理")
@RestController
@RequestMapping("/management/highschool")
@BindResource(value = "highschool:management")
public class HighSchoolManagementController {
    @Autowired
    private HighSchoolService highSchoolService;

    @BindResource(value = "highschool:management:list")
    @Action(value = "查询高中列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<HighSchoolVO> list(@Session RDUserSession user, @RequestBody QueryRequest<HighSchoolFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<HighSchoolEntity> result = highSchoolService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, HighSchoolVO.class));
    }

    @BindResource(value = "highschool:management:create")
    @Action(value = "创建高中信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public HighSchoolEntity create(@Session RDUserSession user, @Valid @RequestBody HighSchoolDto dto)
            throws ServiceException {

        HighSchoolEntity entity = highSchoolService.newEntity(dto);

        entity = highSchoolService.create(entity);

        return entity;
    }

    @BindResource("highschool:management:delete")
    @Action(value = "删除高中", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        highSchoolService.deleteById(id);
    }

    @BindResource("highschool:management:details")
    @Action(value = "查询高中详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public HighSchoolEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return highSchoolService.getById(id);
    }

    @BindResource("highschool:management:update")
    @Action(value = "更新高中信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody HighSchoolDto dto) throws ServiceException {
        // 获取现有的高中实体
        HighSchoolEntity entity = highSchoolService.getById(dto.getId());

        // 将dto中的字段复制到现有的高中实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新高中实体
        highSchoolService.update(entity);
    }

    @BindResource("highschool:management:lists")
    @Action(value = "查询高中列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/lists")
    public List<HighSchoolEntity> lists(){
        return highSchoolService.lists();
    }


}
