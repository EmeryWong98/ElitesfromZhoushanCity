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
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiModule("Students")
@Api(name = "StudentsApi", description = "学生API")
@RestController
@RequestMapping("/api/students")
@BindResource(value = "students:api", menu = false)
public class StudentsApiController {
    @Autowired
    private StudentsService studentsService;

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


    @BindResource("students:api:keyContactCountByAreaAndYear")
    @Action("查询每年的学子数量")
    @PostMapping("/key-contact-count-by-area-and-year")
    public List<YearlyStudentCountDto> getYearlyStudentCount(@RequestBody AcademicYearAndAreaDto academicYearAndAreaDto) throws ServiceException {
        return studentsService.getYearlyStudentCount(academicYearAndAreaDto);
    }

//    @BindResource(value = "students:api:list")
//    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
//    @PostMapping("/list")
//    public Map<String, Object> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
//            throws ServiceException {
//        if (query == null) {
//            query = QueryRequest.create(null);
//        }
//
//        PagingData<StudentsEntity> result = studentsService.queryList(query);
//        long totalCount = studentsService.getTotalCount(); // 获取总学生数
//        double proportion = (double) result.getPageInfo().getDataCount() / totalCount; // 计算占比
//
//        // 在这里进行转换并填充 universityName
//        PagingData<StudentsVO> voResult = result.map((entity) -> {
//            StudentsVO vo = ObjectUtils.copyEntity(entity, StudentsVO.class);
//
//            if (entity.getUniversity() != null) {
//                vo.setUniversityName(entity.getUniversity().getName());
//            }
//
//            return vo;
//        });
//
//        // 构造返回数据
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("pagingData", voResult);
//        responseData.put("proportion", proportion);
//
//        return responseData;
//    }
    @BindResource(value = "students:api:list")
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

            if (entity.getHighSchool() != null) {
                vo.setHighSchoolName(entity.getHighSchool().getName());
            }

            return vo;
        });

    }

    @BindResource("students:api:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return studentsService.getById(id);
    }



}
