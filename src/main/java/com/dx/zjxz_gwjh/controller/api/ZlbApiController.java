package com.dx.zjxz_gwjh.controller.api;

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
import com.dx.zjxz_gwjh.enums.Status;
import com.dx.zjxz_gwjh.enums.ZLBStatus;
import com.dx.zjxz_gwjh.filter.DomesticAssistanceFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.*;
import com.dx.zjxz_gwjh.util.IdCardInfo;
import com.dx.zjxz_gwjh.util.Province;
import com.dx.zjxz_gwjh.vo.DomesticAssistanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@ApiModule("Students")
@Api(name = "ZlbApi", description = "浙里办API")
@RestController
@RequestMapping("/api/zlb")
@BindResource(value = "zlb:api", menu = false)
public class ZlbApiController {

    @Autowired
    private UniversityService universityService;

    @Autowired
    private AreaCodeService areaCodeService;

    @Autowired
    private HighSchoolService highSchoolService;

    @Autowired
    private NetService netService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private DomesticAssistanceService domesticAssistanceService;

    @Autowired
    private ZLBStudentsService zlbStudentsService;

    @BindResource("university:zlb:list")
    @Action(value = "查询大学列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/universitylists")
    public List<UniversityEntity> getUniversitylist(){
        return universityService.lists();
    }

    @BindResource("area:zlb:list")
    @Action(value = "查询地区列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/areacodelists")
    public List<SimpleAreaCodeDto> getAreaCodeList() {
        return areaCodeService.getSimpleAreaCodeList();
    }

    @BindResource("highschool:zlb:list")
    @Action(value = "查询高中列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/highschoollists")
    public List<HighSchoolEntity> getHighSchoollist(){
        return highSchoolService.lists();
    }

    @BindResource("net:zlb:list")
    @Action(value = "查询网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/netlists")
    public NetDetailsDto getNetDetails(String idCard) {
        return netService.getNetDetails(idCard);
    }

    @BindResource("province:zlb:list")
    @Action(value = "查询省份列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/provincelists")
    public List<String> getProvinces() {
        return Province.getAllProvinces();
    }

    @BindResource("jsns:zlb:create")
    @Action(value = "创建家事难事", type = Action.ActionType.CREATE)
    @PostMapping("/jsnscreate")
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

    @BindResource("jsns:zlb:list")
    @Action(value = "家事难事列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/jsnslist")
    public PagingData<DomesticAssistanceVO> list(@Session RDUserSession user, @RequestBody QueryRequest<DomesticAssistanceFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<DomesticAssistanceEntity> result = domesticAssistanceService.queryList(query);
        return result.map((entity) -> ObjectUtils.copyEntity(entity, DomesticAssistanceVO.class));
    }

    @BindResource("jsns:zlb:details")
    @Action(value = "家事难事详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/jsnsdetails")
    public DomesticAssistanceEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return domesticAssistanceService.getById(id);
    }

    @BindResource(value = "zlbStudents:zlb:create")
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



    

}
