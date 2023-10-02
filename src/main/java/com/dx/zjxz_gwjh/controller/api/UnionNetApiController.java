package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.dto.UnionNetStudentsDto;
import com.dx.zjxz_gwjh.dto.UnionRequestDto;
import com.dx.zjxz_gwjh.service.UnionNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiModule("Students")
@Api(name = "UnionNetApi", description = "学联网格API")
@RestController
@RequestMapping("/api/unionnet")
@BindResource(value = "unionnet:api", menu = false)
public class UnionNetApiController {

    @Autowired
    private UnionNetService unionNetService;

    @BindResource("unionnet:api:ranking")
    @Action("查询学联网格活跃度排名")
    @PostMapping("/ranking")
    public List<NetActivityDto> getUnionNetActivityRanking() {
        return unionNetService.getUnionNetActivityRanking();
    }

    @BindResource("unionnet:api:studentslist")
    @Action("查询学联网格学生列表")
    @PostMapping("/studentslist")
    public UnionNetStudentsDto getUnionNetStudentsList(@RequestParam("id") String id) {
        return unionNetService.getUnionNetStudentsList(id);
    }

    @BindResource("unionnet:api:netcount")
    @Action("查询学联网格数量")
    @PostMapping("/netcount")
    public Integer getUnionNetCount() {
        return unionNetService.getUnionNetCount();
    }
}
