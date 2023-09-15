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
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@ApiModule("Students")
@Api(name = "StudentsManagement", description = "学子管理")
@RestController
@RequestMapping("/management/students")
@BindResource(value = "students:management")
public class StudentsManagementController {
    @Autowired
    private StudentsService studentsService;

    @BindResource(value = "students:management:list")
    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<StudentsVO> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<StudentsEntity> result = studentsService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, StudentsVO.class));
    }


    @BindResource(value = "students:management:create")
    @Action(value = "创建学生信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public StudentsEntity create(@Session RDUserSession user, @Valid @RequestBody StudentsDto dto)
            throws ServiceException {

        // 使用新的createStudent方法来创建或更新学生实体
        StudentsEntity entity = studentsService.createStudent(dto);

        // 其他逻辑（如果有的话）...

        return entity;
    }


    @BindResource("students:management:delete")
    @Action(value = "删除学生", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        studentsService.deleteById(id);
    }

    @BindResource("students:management:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return studentsService.getById(id);
    }

    @BindResource("students:management:update")
    @Action(value = "更新学生信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody StudentsDto dto) throws ServiceException {
        // 获取现有的学生实体
        StudentsEntity entity = studentsService.getById(dto.getId());

        // 将dto中的字段复制到现有的学生实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新学生实体
        studentsService.update(entity);
    }


}
