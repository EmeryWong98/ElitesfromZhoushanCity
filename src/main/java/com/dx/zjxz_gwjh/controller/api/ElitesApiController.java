package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.easyspringweb.core.utils.ObjectUtils;
import com.dx.zjxz_gwjh.dto.AcademicYearDto;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.service.UniversityService;
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

    @BindResource("elites:api:countbyareaandprovince")
    @Action("根据属地和省份统计重点学子数量和高校数量")
    @PostMapping("/elites-count-by-area-and-province")
    public ResponseEntity<Map<String, Map<String, Map<String, Integer>>>> getKeyContactCountByAreaAndProvince() {
        Map<String, Map<String, Map<String, Integer>>> countMap = studentsService.getKeyContactCountByAreaProvinceAndUniversity();
        return new ResponseEntity<>(countMap, HttpStatus.OK);
    }

    @BindResource("elites:api:countbyacademicyear")
    @Action("根据学年和属地统计重点学子数量")
    @PostMapping("/elite-count-by-academic-year")
    public ResponseEntity<Map<String, Integer>> getKeyContactCountByAcademicYear(@Valid @RequestBody AcademicYearDto academicYearDto) throws ServiceException {
        Map<String, Integer> countMap = studentsService.getKeyContactCountByAcademicYearAndArea(academicYearDto);
        return new ResponseEntity<>(countMap, HttpStatus.OK);
    }

    @BindResource("elites:api:keyContactCountByAreaAndYear")
    @Action("根据学年和属地统计重点学子数量")
    @PostMapping("/key-contact-count-by-area-and-year")
    public ResponseEntity<Map<String, Map<String, Object>>> getKeyContactCountByAreaAndYear() {
        Map<String, Map<String, Object>> countMap = studentsService.getKeyContactCountByAreaAndAcademicYear();
        return new ResponseEntity<>(countMap, HttpStatus.OK);
    }

}
