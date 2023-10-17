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
import com.dx.zjxz_gwjh.dto.NetActivityDto;
import com.dx.zjxz_gwjh.dto.UnionNetStudentsDto;
import com.dx.zjxz_gwjh.dto.UnionRequestDto;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.filter.StudentsFilter;
import com.dx.zjxz_gwjh.model.RDUserSession;
import com.dx.zjxz_gwjh.service.DegreeBindingService;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.service.UnionNetService;
import com.dx.zjxz_gwjh.vo.StudentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiModule("Students")
@Api(name = "UnionNetApi", description = "学联网格API")
@RestController
@RequestMapping("/api/unionnet")
@BindResource(value = "unionnet:api", menu = false)
public class UnionNetApiController {

    @Autowired
    private UnionNetService unionNetService;

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @BindResource("unionnet:api:ranking")
    @Action("查询学联网格活跃度排名")
    @PostMapping("/ranking")
    public List<NetActivityDto> getUnionNetActivityRanking() {
        return unionNetService.getUnionNetActivityRanking();
    }

    @BindResource("unionnet:api:netlist")
    @Action("查询学联网格列表")
    @PostMapping("/netlist")
    public UnionNetStudentsDto getUnionNetStudentsList(@RequestParam("id") String id) {
        return unionNetService.getUnionNetStudentsList(id);
    }

    @BindResource("unionnet:api:studentlist")
    @Action("查询学联网格学生列表")
    @PostMapping("/studentlist")
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

    @BindResource("unionnet:api:nets")
    @Action("查询学联网格")
    @PostMapping("/nets")
    public List<UnionRequestDto> getUnionNetList() {
        return unionNetService.getUnionNetList();
    }


    @BindResource("unionnet:api:netcount")
    @Action("查询学联网格数量")
    @PostMapping("/netcount")
    public Integer getUnionNetCount() {
        return unionNetService.getUnionNetCount();
    }


}
