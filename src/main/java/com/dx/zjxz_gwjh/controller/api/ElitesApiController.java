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
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.service.UniversityService;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import com.dx.zjxz_gwjh.vo.UniversityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public PagingData<StudentsVO> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
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
            StudentsVO vo = ObjectUtils.copyEntity(entity, StudentsVO.class);

            if (entity.getHighSchool() != null) {
                vo.setHighSchoolName(entity.getHighSchool().getName());
            }

            return vo;
        });
    }

    @BindResource("elites:api:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return studentsService.getById(id);
    }





}
