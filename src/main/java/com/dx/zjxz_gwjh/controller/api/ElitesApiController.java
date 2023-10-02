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
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.filter.UniversityFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.repository.HighSchoolNetRepository;
import com.dx.zjxz_gwjh.service.*;
import com.dx.zjxz_gwjh.vo.ElitesVO;
import com.dx.zjxz_gwjh.vo.StudentsDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import com.dx.zjxz_gwjh.vo.UniversityVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApiModule("Students")
@Api(name = "ElitesApi", description = "重点学子API")
@RestController
@RequestMapping("/api/elites")
@BindResource(value = "elites:api", menu = false)
public class ElitesApiController {
    @Autowired
    private StudentsService studentsService;

    @Autowired
    private UniversityService universityService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private OfficerNetService officerNetService;

    @Autowired
    private UnionNetService unionNetService;

    @BindResource("elites:api:countbymap")
    @Action("查询地图显示学子")
    @PostMapping("/student-count-by-map")
    public List<MapCountDto> getKeyMapCountByAcademicYearAndArea(@RequestBody AcademicYearAndAreaDto request) throws ServiceException {
        return studentsService.getKeyMapCountByAcademicYearAndArea(request);
    }

    @BindResource("elites:api:countbyacademicyear")
    @Action("查询学子总数")
    @PostMapping("/student-count-by-academic-year")
    public List<EliteCountDto> getKeyStudentCountByAcademicYear(@Valid @RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getKeyStudentCountByAcademicYearAndArea(academicYearAndAreaDto);
    }

    @BindResource("elites:api:countbyacademicyeardefault")
    @Action("默认查询学子总数")
    @PostMapping("/student-count-by-academic-year-default")
    public List<EliteCountDto> getKeyStudentCountByAcademicYearAndAreaDefault(@Valid @RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getKeyStudentCountByAcademicYearAndAreaDefault(academicYearAndAreaDto);
    }

    @BindResource("elites:api:countbyyear")
    @Action("查询每年的学子数量")
    @PostMapping("/count-by-year")
    public List<YearlyStudentCountDto> getKeyYearlyStudentCount(@RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getKeyYearlyStudentCount(academicYearAndAreaDto);
    }

    @BindResource("elites:api:universitylist")
    @Action(value = "查询高校列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/universitylist")
    public EliteUniversityListDto getEliteUniversityList(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {
        if(query == null || query.getFilter() == null) {
            throw new ServiceException("Invalid request: Missing required filter parameters");
        }
        return universityService.getEliteUniversityList(query.getFilter());
    }

    @BindResource("elites:api:studentslist")
    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/studentslist")
    public PagingData<ElitesVO> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        if (query.getFilter() == null) {
            query.setFilter(new StudentsFilter());
        }

        // 设置 isKeyContact 为 true，以便仅返回重点学子
        query.getFilter().setIsKeyContact(true);

        PagingData<StudentsEntity> result = studentsService.queryList(query);

        return result.map((entity) -> {
            ElitesVO vo = ObjectUtils.copyEntity(entity, ElitesVO.class);

            // 设置学生的大学名称、省份、学历、专业
            vo.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(entity.getId()));
            vo.setDegree(degreeBindingService.findHighestDegreeByStudentId(entity.getId()));
            vo.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(entity.getId()));
            vo.setHighSchoolNetName(highSchoolNetService.findNameById(entity.getHighSchoolNetId()));
            vo.setAreaNetName(areaNetService.findNameById(entity.getAreaNetId()));
            vo.setOfficerNetName(officerNetService.findNameById(entity.getOfficerNetId()));
            vo.setUnionNetName(unionNetService.findNameById(entity.getUnionNetId()));

            return vo;
        });
    }

    @BindResource("elites:api:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsDetailsVO details(@RequestParam("id") String id)
            throws ServiceException {
        StudentsEntity studentEntity = studentsService.getById(id);

        StudentsDetailsVO studentDetails = new StudentsDetailsVO();
        // 复制 StudentsEntity 的属性到 StudentDetailsVO
        BeanUtils.copyProperties(studentEntity, studentDetails);

        // 设置 StudentDetailsVO 的额外字段
        studentDetails.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(studentEntity.getId()));
        studentDetails.setUniversityProvince(degreeBindingService.findHighestDegreeUniversityProvinceByStudentId(studentEntity.getId()));
        studentDetails.setDegree(degreeBindingService.findHighestDegreeByStudentId(studentEntity.getId()));
        studentDetails.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(studentEntity.getId()));

        return studentDetails;
    }


}
