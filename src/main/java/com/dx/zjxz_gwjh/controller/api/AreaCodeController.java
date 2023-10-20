package com.dx.zjxz_gwjh.controller.api;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.dto.AreaCodeDto;
import com.dx.zjxz_gwjh.dto.SimpleAreaCodeDto;
import com.dx.zjxz_gwjh.dto.StaticAreaCodeDto;
import com.dx.zjxz_gwjh.service.AreaCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiModule("Students")
@Api(name = "AreaCodeApi", description = "区域网格API")
@RestController
@RequestMapping("/api/areacode")
@BindResource(value = "areacode:api", menu = false)
public class AreaCodeController {
    @Autowired
    private AreaCodeService areaCodeService;

    @BindResource("areacode:api:list")
    @Action("查询属地列表")
    @PostMapping("/list")
    public List<StaticAreaCodeDto> getAreaCodeListStatic() {
        return areaCodeService.getAreaCodeListStatic();
    }

    @BindResource("areacode:api:simplelist")
    @Action(value = "查询地区列表", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/simplelist")
    public List<SimpleAreaCodeDto> getAreaCodeList() {
        return areaCodeService.getSimpleAreaCodeList();
    }
}
