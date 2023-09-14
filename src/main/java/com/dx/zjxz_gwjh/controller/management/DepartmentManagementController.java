package com.dx.zjxz_gwjh.controller.management;

import org.springframework.web.bind.annotation.RestController;

import com.dx.zjxz_gwjh.dto.DepartmentDto;
import com.dx.zjxz_gwjh.entity.DepartmentEntity;
import com.dx.zjxz_gwjh.filter.DepartmentFilter;
import com.dx.zjxz_gwjh.service.DepartmentService;
import com.dx.easyspringweb.biz.controller.management.BaseDepartmentManagementController;

@RestController
public class DepartmentManagementController extends
        BaseDepartmentManagementController<DepartmentService, DepartmentEntity, DepartmentDto, DepartmentFilter, String> {

    public DepartmentManagementController(DepartmentService service) {
        super(service);
    }
}
