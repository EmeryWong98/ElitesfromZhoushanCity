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
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.*;
import com.dx.zjxz_gwjh.enums.DegreeType;
import com.dx.zjxz_gwjh.enums.ZLBStatus;
import com.dx.zjxz_gwjh.filter.HighSchoolFilter;
import com.dx.zjxz_gwjh.filter.ZLBStudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.repository.DegreeBindingRepository;
import com.dx.zjxz_gwjh.repository.StudentsRepository;
import com.dx.zjxz_gwjh.repository.UniversityRepository;
import com.dx.zjxz_gwjh.service.*;
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

    @Autowired
    private HighSchoolService highSchoolService;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private DegreeBindingRepository degreeBindingRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private OfficerNetService officerNetService;

    @Autowired
    private UnionNetService UnionNetService;

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
        try {
            DegreeType degree = DegreeType.fromDescription(dto.getDegree());
            entity.setDegree(degree); // 存储描述到entity
        } catch (ServiceException e) {
            throw new ServiceException("无效的学位描述");
        }
        entity.setArea(dto.getArea());
        entity.setUniversityProvince(dto.getUniversityProvince());
        entity.setStatus(ZLBStatus.Processing);

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
    public ZLBStudentDetailsDto details(@RequestParam("id") String id)
            throws ServiceException {
        ZLBStudentsEntity entity = zlbStudentsService.getById(id);

        ZLBStudentDetailsDto dto = new ZLBStudentDetailsDto();
        ObjectUtils.copyEntity(entity, dto);
        dto.setHighSchoolId(highSchoolService.findByName(entity.getHighSchool()).getId());

        if (entity.getUniversity() != null) {
            switch (entity.getDegree()) {
                case Undergraduate:
                    dto.setUniversity1Name(entity.getUniversity());
                    dto.setDegree1(DegreeType.Undergraduate.getDescription());
                    dto.setMajor1(entity.getMajor());
                    dto.setUniversity1Province(entity.getUniversityProvince());
                    break;
                case Graduate:
                    dto.setUniversity2Name(entity.getUniversity());
                    dto.setDegree2(DegreeType.Graduate.getDescription());
                    dto.setMajor2(entity.getMajor());
                    dto.setUniversity2Province(entity.getUniversityProvince());
                    break;
                case PHD:
                    dto.setUniversity3Name(entity.getUniversity());
                    dto.setDegree3(DegreeType.PHD.getDescription());
                    dto.setMajor3(entity.getMajor());
                    dto.setUniversity3Province(entity.getUniversityProvince());
                    break;
            }


        }

        return dto;
    }

    @BindResource("zlbstudents:management:update")
    @Action(value = "更新浙里办学生信息", type = Action.ActionType.UPDATE)
    @PostMapping("/update")
    public void update(@Valid @RequestBody ZLBAuditDto dto) throws ServiceException {
        // 获取现有的浙里办学生实体
        ZLBStudentsEntity entity = zlbStudentsService.getByIdCard(dto.getIdCard());

        ObjectUtils.copyEntity(dto, entity);

        if (dto.getDegree1() != null) {
            entity.setUniversity(dto.getUniversity1Name());
            entity.setDegree(DegreeType.fromDescription(dto.getDegree1()));
            entity.setMajor(dto.getMajor1());
            entity.setUniversityProvince(dto.getUniversity1Province());
        } else if (dto.getDegree2() != null) {
            entity.setUniversity(dto.getUniversity2Name());
            entity.setDegree(DegreeType.fromDescription(dto.getDegree2()));
            entity.setMajor(dto.getMajor2());
            entity.setUniversityProvince(dto.getUniversity2Province());
        } else if (dto.getDegree3() != null) {
            entity.setUniversity(dto.getUniversity3Name());
            entity.setDegree(DegreeType.fromDescription(dto.getDegree3()));
            entity.setMajor(dto.getMajor3());
            entity.setUniversityProvince(dto.getUniversity3Province());
        } else {
            throw new ServiceException("无效的学位描述");
        }

        entity.setHighSchool(highSchoolService.findById(dto.getHighSchoolId()).getName());

        // 更新浙里办学生实体;
        zlbStudentsService.update(entity);

        if(dto.getStatus() == ZLBStatus.Succeed) {
            StudentsCreateDto studentDto = new StudentsCreateDto();
            ObjectUtils.copyEntity(dto, studentDto);

            // 创建学生实体
            studentsService.createStudent(studentDto);

        }
    }
}
