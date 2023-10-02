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
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.DegreeBindingService;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.StudentsDetailsVO;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("Students")
@Api(name = "StudentsApi", description = "学生API")
@RestController
@RequestMapping("/api/students")
@BindResource(value = "students:api", menu = false)
public class StudentsApiController {
    @Autowired
    private StudentsService studentsService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @BindResource("students:api:countbymap")
    @Action("查询地图显示学子")
    @PostMapping("/student-count-by-map")
    public List<MapCountDto> getMapCountByAcademicYearAndArea(@RequestBody AcademicYearAndAreaDto request) throws ServiceException {
        return studentsService.getMapCountByAcademicYearAndArea(request);
    }

    @BindResource("students:api:countbyacademicyear")
    @Action("查询学子总数")
    @PostMapping("/student-count-by-academic-year")
    public List<StudentCountDto> getStudentCountByAcademicYear(@Valid @RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getStudentCountByAcademicYearAndArea(academicYearAndAreaDto);
    }

    @BindResource("students:api:countbyacademicyeardefault")
    @Action("默认查询学子总数")
    @PostMapping("/student-count-by-academic-year-default")
    public List<StudentCountDto> getStudentCountByAcademicYearAndAreaDefault(@Valid @RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getStudentCountByAcademicYearAndAreaDefault(academicYearAndAreaDto);
    }


    @BindResource("students:api:countbyyear")
    @Action("查询每年的学子数量")
    @PostMapping("/count-by-year")
    public List<YearlyStudentCountDto> getYearlyStudentCount(@RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getYearlyStudentCount(academicYearAndAreaDto);
    }

    @BindResource("students:api:list")
    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public PagingData<StudentsVO> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<StudentsEntity> result = studentsService.queryList(query);

        return result.map((entity) -> {
            StudentsVO vo = ObjectUtils.copyEntity(entity, StudentsVO.class);
            vo.setUniversityName(degreeBindingService.findHighestDegreeUniversityNameByStudentId(entity.getId()));
            vo.setUniversityProvince(degreeBindingService.findHighestDegreeUniversityProvinceByStudentId(entity.getId()));
            vo.setDegree(degreeBindingService.findHighestDegreeByStudentId(entity.getId()));
            vo.setMajor(degreeBindingService.findHighestDegreeMajorByStudentId(entity.getId()));
            return vo;
        });
    }

    @BindResource("students:api:details")
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
