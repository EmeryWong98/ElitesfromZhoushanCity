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
import com.dx.zjxz_gwjh.dto.AcademicYearDto;
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

    @BindResource("students:api:countbyareaandprovince")
    @Action("根据属地和省份统计学生数量")
    @PostMapping("/student-count-by-area-and-province")
    public Map<String, Map<String, Integer>> getStudentCountByAreaAndProvince() {
        return studentsService.getStudentCountByAreaAndProvince();
    }

    @BindResource("students:api:countbyacademicyear")
    @Action("根据学年和属地统计学生数量")
    @PostMapping("/student-count-by-academic-year")
    public Map<String, Integer> getStudentCountByAcademicYear(@Valid @RequestBody AcademicYearDto academicYearDto) throws ServiceException {
        return studentsService.getStudentCountByAcademicYearAndArea(academicYearDto);
    }

    @BindResource("students:api:keyContactCountByAreaAndYear")
    @Action("根据学年和属地统计重点学子数量")
    @PostMapping("/key-contact-count-by-area-and-year")
    public Map<String, Map<String, Object>> getKeyContactCountByAreaAndYear() {
        return studentsService.getKeyContactCountByAreaAndAcademicYear();
    }

    @BindResource(value = "students:api:list")
    @Action(value = "查询学生列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/list")
    public Map<String, Object> list(@Session RDUserSession user, @RequestBody QueryRequest<StudentsFilter> query)
            throws ServiceException {
        if (query == null) {
            query = QueryRequest.create(null);
        }

        PagingData<StudentsEntity> result = studentsService.queryList(query);
        long totalCount = studentsService.getTotalCount(); // 获取总学生数
        double proportion = (double) result.getPageInfo().getDataCount() / totalCount; // 计算占比

        // 在这里进行转换并填充 universityName
        PagingData<StudentsVO> voResult = result.map((entity) -> {
            StudentsVO vo = ObjectUtils.copyEntity(entity, StudentsVO.class);

            if (entity.getUniversity() != null) {
                vo.setUniversityName(entity.getUniversity().getName());
            }

            return vo;
        });

        // 构造返回数据
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("pagingData", voResult);
        responseData.put("proportion", proportion);

        return responseData;
    }




}
