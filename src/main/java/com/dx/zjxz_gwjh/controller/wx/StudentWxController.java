package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import com.dx.zjxz_gwjh.service.DegreeBindingService;
import com.dx.zjxz_gwjh.service.StudentsService;
import com.dx.zjxz_gwjh.vo.StudentsDetailsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@ApiModule("Students")
@Api(name = "StudentsApi", description = "学生API")
@RestController
@RequestMapping("/wx/students")
@BindResource(value = "students:wx", menu = false)
public class StudentWxController {

    @Autowired
    private StudentsService studentsService;

    @Autowired
    private DegreeBindingService degreeBindingService;

    @BindResource("students:wx:details")
    @Action(value = "手机端查询学生详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentsDetailsVO details(@RequestParam("id") String id) throws ServiceException {
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
