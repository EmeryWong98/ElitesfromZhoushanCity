package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.annotation.Session;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.DomesticAssistanceDto;
import com.dx.zjxz_gwjh.dto.NetDetailsDto;
import com.dx.zjxz_gwjh.dto.SimpleAreaCodeDto;
import com.dx.zjxz_gwjh.dto.StaticAreaCodeDto;
import com.dx.zjxz_gwjh.entity.DomesticAssistanceEntity;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.enums.Status;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @BindResource("university:management:list")
    @Action(value = "查询大学列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/universitylists")
    public List<UniversityEntity> getUniversitylist(){
        return universityService.lists();
    }

    @BindResource("area:management:list")
    @Action(value = "查询地区列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/areacodelists")
    public List<SimpleAreaCodeDto> getAreaCodeList() {
        return areaCodeService.getSimpleAreaCodeList();
    }

    @BindResource("highschool:management:list")
    @Action(value = "查询高中列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/highschoollists")
    public List<HighSchoolEntity> getHighSchoollist(){
        return highSchoolService.lists();
    }

    @BindResource("net:management:list")
    @Action(value = "查询网格列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/netlists")
    public NetDetailsDto getNetDetails(String idCard) {
        return netService.getNetDetails(idCard);
    }

    @BindResource("jsns:management:create")
    @Action(value = "创建家事难事信息", type = Action.ActionType.CREATE)
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

    

}
