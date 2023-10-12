package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.Authenticated;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.PagingData;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.DomesticAssistanceDto;
import com.dx.zjxz_gwjh.dto.HighSchoolDto;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.enums.Status;
import com.dx.zjxz_gwjh.filter.DomesticAssistanceFilter;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.DomesticAssistanceService;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.DomesticAssistanceVO;
import com.dx.zjxz_gwjh.vo.HighSchoolVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("DomesticAssistance")
@Api(name = "DomesticAssistanceManagement", description = "家事难事管理")
@RestController
@RequestMapping("/management/domesticassistance")
@BindResource(value = "domesticassistance:management")
public class DomesticAssistanceManagementController {

    @Autowired
    private DomesticAssistanceService domesticAssistanceService;

    @Autowired
    private StudentsService studentsService;

    @BindResource("domesticassistance:management:list")
    @Action(value = "查询家事难事列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<DomesticAssistanceVO> list(@Session RDUserSession user, @RequestBody QueryRequest<DomesticAssistanceFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<DomesticAssistanceEntity> result = domesticAssistanceService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, DomesticAssistanceVO.class));
    }

    @BindResource(value = "domesticassistance:management:create")
    @Action(value = "创建家事难事信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public DomesticAssistanceEntity create(@Session RDUserSession user, @Valid @RequestBody DomesticAssistanceDto dto)
            throws ServiceException {
        StudentsEntity student = studentsService.findByIdCard(dto.getIdCard());
        if (student == null) {
            throw new ServiceException("未找到学生信息，请先填报舟籍学子");
        }

        DomesticAssistanceEntity entity = new DomesticAssistanceEntity();
        entity.setStudentId(student.getId());
        entity.setStudentName(dto.getStudentName());
        entity.setPhone(dto.getPhone());
        entity.setIdCard(dto.getIdCard());
        entity.setArea(dto.getArea());
        entity.setContent(dto.getContent());
        entity.setStatus(Status.Wait);

        domesticAssistanceService.create(entity);

        return entity;
    }

    @BindResource("domesticassistance:management:update")
    @Action(value = "更新家事难事信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody DomesticAssistanceDto dto) throws ServiceException {
        // 获取现有的高中实体
        DomesticAssistanceEntity entity = domesticAssistanceService.getById(dto.getId());

        // 将dto中的字段复制到现有的高中实体中
        ObjectUtils.copyEntity(dto, entity);

        // 更新高中实体
        domesticAssistanceService.update(entity);
    }

    @BindResource("domesticassistance:management:delete")
    @Action(value = "删除家事难事", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        domesticAssistanceService.deleteById(id);
    }

    @BindResource("domesticassistance:management:details")
    @Action(value = "查询家事难事详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public DomesticAssistanceEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return domesticAssistanceService.getById(id);
    }

}
