package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.dto.HighSchoolNetOverviewDto;
import com.dx.zjxz_gwjh.service.HighSchoolNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiModule("Students")
@Api(name = "HighSchoolNetApi", description = "高中网格API")
@RestController
@RequestMapping("/api/highschoolnet")
@BindResource(value = "highschoolnet:api", menu = false)
public class HighSchoolNetApiController {
    @Autowired
    private HighSchoolNetService highSchoolNetService;

    @BindResource("highschoolnet:api:overview")
    @Action("查询高中网格概况")
    @PostMapping("/overview")
    public List<HighSchoolNetOverviewDto> getHighSchoolNetOverview() {
        return highSchoolNetService.getHighSchoolNetOverview();
    }
}
