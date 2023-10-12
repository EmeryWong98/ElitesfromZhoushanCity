package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.*;
import com.dx.easyspringweb.core.annotation.*;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.model.QueryRequest;
import com.dx.zjxz_gwjh.dto.StudentBackAreaCountDto;
import com.dx.zjxz_gwjh.dto.StudentBackCountDto;
import com.dx.zjxz_gwjh.dto.StudentBackCountQueryDto;
import com.dx.zjxz_gwjh.dto.StudentBackYearCountDto;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.StudentJourneyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@ApiModule("Students")
@Api(name = "StudentsBackApi", description = "回舟学子API")
@RestController
@RequestMapping("/api/students/back")
@BindResource(value = "studentBack:api", menu = false)
public class StudentsBackApiController {
    @Autowired
    private StudentJourneyLogService studentJourneyLogService;

    @BindResource("students:api:count")
    @Action(value = "查询回舟学生数量")
    @PostMapping("/count")
    public StudentBackCountDto getStudentBackCount(@Session RDUserSession user, @RequestBody QueryRequest<StudentBackCountQueryDto> query) throws ServiceException {
        return studentJourneyLogService.queryStudentBackCount(query.getFilter());
    }
    @BindResource("students:api:areaCount")
    @Action(value = "查询回舟学生地区数量")
    @PostMapping("/areaCount")
    public List<StudentBackAreaCountDto> getStudentBackAreaCount(@Session RDUserSession user, @RequestBody QueryRequest<StudentBackCountQueryDto> query) throws ServiceException {
        return studentJourneyLogService.queryStudentBackAreaCount(query.getFilter());
    }
    @BindResource("students:api:yearCount")
    @Action(value = "查询回舟学生届次数量")
    @PostMapping("/yearCount")
    public List<StudentBackYearCountDto> getStudentBackYearCount(@Session RDUserSession user, @RequestBody QueryRequest<StudentBackCountQueryDto> query) throws ServiceException {
        return studentJourneyLogService.queryStudentBackYearCount(query.getFilter());
    }
}
