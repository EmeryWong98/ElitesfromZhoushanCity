package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.service.HighSchoolNetService;
import com.dx.zjxz_gwjh.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiModule("Students")
@Api(name = "HighSchoolNetApi", description = "高中网格API")
@RestController
@RequestMapping("/api/highschoolnet")
@BindResource(value = "highschoolnet:api", menu = false)
public class HighSchoolNetApiController {
    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @Autowired
    private StudentsService studentsService;

    @BindResource("highschoolnet:api:overview")
    @Action("查询高中网格概况")
    @PostMapping("/overview")
    public List<HighSchoolNetOverviewDto> getHighSchoolNetOverview() {
        return highSchoolNetService.getHighSchoolNetOverview();
    }

    @BindResource("highschoolnet:api:ranking")
    @Action("查询高中网格活跃度排名")
    @PostMapping("/ranking")
    public List<HighSchoolNetActivityDto> getHighSchoolNetActivityRanking() {
        return highSchoolNetService.getHighSchoolNetActivityRanking();
    }

    @BindResource("highschoolnet:api:simpleOverview")
    @Action("查询简化版高中网格概况")
    @PostMapping("/simpleOverview")
    public List<HighSchoolNetSimpleOverviewDto> getHighSchoolNetSimpleOverview() {
        return highSchoolNetService.getHighSchoolNetSimpleOverview();
    }

    @BindResource("highschoolnet:api:teachersAndStudents")
    @Action("查询高中网格教师和学生")
    @PostMapping("/teachersAndStudents")
    public List<TeacherStudentDto> getTeachersAndStudents(@RequestBody HighSchoolRequestDto highSchoolRequestDto) {
        return highSchoolNetService.getTeachersAndStudents(highSchoolRequestDto);
    }

    @BindResource("highschoolnet:api:details")
    @Action(value = "查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsEntity details(@RequestParam("id") String id)
            throws ServiceException {
        return studentsService.getById(id);
    }


}
