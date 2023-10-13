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
import com.dx.zjxz_gwjh.dto.StudentsCreateDto;
import com.dx.zjxz_gwjh.dto.StudentsDto;
import com.dx.zjxz_gwjh.dto.ZLBStudentsDto;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.ZLBStudentsEntity;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.filter.ZLBStudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.service.ZLBStudentsService;
import com.dx.zjxz_gwjh.util.IdCardInfo;
import com.dx.zjxz_gwjh.vo.HighSchoolVO;
import com.dx.zjxz_gwjh.vo.ZLBStudentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@ApiModule("ZLBStudents")
@Api(name = "ZLBStudentsManagement", description = "浙里办学子管理")
@RestController
@RequestMapping("/management/zlbstudents")
@BindResource(value = "zlbstudents:management")
public class ZLBStudentsManagementController {
    @Autowired
    private ZLBStudentsService zlbStudentsService;

    @Autowired
    private StudentsService studentsService;

    @BindResource(value = "zlbstudents:management:list")
    @Action(value = "查询浙里办学子列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<ZLBStudentsVO> list(@Session RDUserSession user, @RequestBody QueryRequest<ZLBStudentsFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<ZLBStudentsEntity> result = zlbStudentsService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, ZLBStudentsVO.class));
    }

    @BindResource(value = "zlbstudents:management:create")
    @Action(value = "创建浙里办学子信息", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public ZLBStudentsEntity create(@Session RDUserSession user, @Valid @RequestBody ZLBStudentsDto dto)
            throws ServiceException {
        StudentsEntity student = studentsService.findByIdCard(dto.getIdCard());
        if (student != null) {
            throw new ServiceException("学生重复，请勿重复填报");
        }

        ZLBStudentsEntity entity = new ZLBStudentsEntity();
        entity.setName(dto.getName());
        entity.setIdCard(dto.getIdCard());
        entity.setPhone(dto.getPhone());
        entity.setHighSchool(dto.getHighSchool());
        entity.setUniversity(dto.getUniversity());
        entity.setMajor(dto.getMajor());
        entity.setAcademicYear(dto.getAcademicYear());
        entity.setAddress(dto.getAddress());
        entity.setDegree(dto.getDegree());
        entity.setArea(dto.getArea());
        entity.setUniversityProvince(dto.getUniversityProvince());
        entity.setIsShow(true);

        // 从身份证中提取出生日期和性别
        try {
            java.util.Date birthDate = IdCardInfo.getBirthDate(dto.getIdCard()); // 假设 IdCardInfo 类存在
            java.sql.Date sqlBirthDate = new java.sql.Date(birthDate.getTime());  // 转换为 java.sql.Date
            String gender = IdCardInfo.getGender(dto.getIdCard());     // 假设 IdCardInfo 类存在

            entity.setDob(sqlBirthDate); // 假设您的 StudentsEntity 有一个叫做 'dob' 的字段
            entity.setSex(gender); // 假设您的 StudentsEntity 有一个叫做 'gender' 的字段
        } catch (ParseException e) {
            throw new ServiceException("身份证格式错误");
        }

        entity = zlbStudentsService.create(entity);

        return entity;
    }

    @BindResource("zlbstudents:management:delete")
    @Action(value = "删除浙里办学子", type = Action.ActionType.DELETE)
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) throws ServiceException {
        zlbStudentsService.deleteById(id);
    }

    @BindResource("zlbstudents:management:details")
    @Action(value = "查询浙里办学子详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public ZLBStudentsEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return zlbStudentsService.getById(id);
    }

    @BindResource("zlbstudents:management:update")
    @Action(value = "更新浙里办学生信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody StudentsCreateDto dto) throws ServiceException {
        // 获取现有的浙里办学生实体
        ZLBStudentsEntity entity = zlbStudentsService.getByIdCard(dto.getIdCard());

        // 创建学生实体
        studentsService.createStudent(dto);

        // 更新浙里办学生实体的 isShow 字段
        entity.setIsShow(false);
        zlbStudentsService.update(entity);
    }


}
