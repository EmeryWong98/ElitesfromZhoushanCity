package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.dto.*;
import com.dx.zjxz_gwjh.entity.AreaNetEntity;
import com.dx.zjxz_gwjh.repository.AreaNetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dx.zjxz_gwjh.service.AreaNetService;

import java.util.List;

@ApiModule("Students")
@Api(name = "AreaNetApi", description = "家庭属地网格API")
@RestController
@RequestMapping("/api/areanet")
@BindResource(value = "areanet:api", menu = false)
public class AreaNetApiController {
    @Autowired
    private AreaNetService areaNetService;

    @Autowired
    private AreaNetRepository areaNetRepository;

    @BindResource("areanet:api:overview")
    @Action("查询家庭属地网格概况")
    @PostMapping("/overview")
    public List<AreaNetOverviewDto> getAreaNetOverview() {
        return areaNetService.getAreaNetOverview();
    }

    @BindResource("areanet:api:ranking")
    @Action("查询家庭属地网格活跃度排名")
    @PostMapping("/ranking")
    public List<NetActivityDto> getAreaNetActivityRanking() {
        return areaNetService.getAreaNetActivityRanking();
    }

    @BindResource("areanet:api:teachersAndStudents")
    @Action("查询家庭属地网格教师和学生")
    @PostMapping("/teachersAndStudents")
    public List<TeacherStudentDto> getTeachersAndStudents(@RequestBody AreaRequestDto areaRequestDto) {
        return areaNetService.getTeachersAndStudents(areaRequestDto);
    }

    @BindResource("areanet:api:netlist")
    @Action("查询家庭属地网格列表")
    @PostMapping("/netlist")
    public List<AreaNetEntity> getAreaNetList(@RequestParam("id") String id) {
        return areaNetService.getAreaNetList(id);
    }



}
