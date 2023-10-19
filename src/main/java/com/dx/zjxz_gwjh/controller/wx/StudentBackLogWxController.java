package com.dx.zjxz_gwjh.controller.wx;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.easyspringweb.core.exception.ServiceException;
import com.dx.zjxz_gwjh.dto.StudentJourneyLogDto;
import com.dx.zjxz_gwjh.entity.StudentJourneyLogEntity;
import com.dx.zjxz_gwjh.service.StudentJourneyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@ApiModule("Students")
@Api(name = "StudentsBackApi", description = "微信回舟学子API")
@RestController
@RequestMapping("/wx/students/back")
@BindResource(value = "studentBack:wx", menu = false)
public class StudentBackLogWxController {

    @Autowired
    private StudentJourneyLogService studentJourneyLogService;

    @BindResource(value = "studentBack:wx:create")
    @Action(value = "微信端创建学子工作", type = Action.ActionType.CREATE)
    @PostMapping("/create")
    public StudentJourneyLogEntity create(@Valid @RequestBody StudentJourneyLogDto dto) throws ServiceException {
        StudentJourneyLogEntity entity = studentJourneyLogService.newEntity(dto);
        entity = studentJourneyLogService.create(entity);
        return entity;
    }

    @BindResource("studentBack:wx:details")
    @Action(value = "微信端学子工作详情", type = Action.ActionType.QUERY_ITEM)
    @PostMapping("/details")
    public StudentJourneyLogEntity details(@RequestParam("id") String id) throws ServiceException {
        List<StudentJourneyLogEntity> logList = studentJourneyLogService.queryByStudentId(id);
        if (!logList.isEmpty()) {
            return logList.get(0);
        } else {
            return new StudentJourneyLogEntity();
        }
    }
}
