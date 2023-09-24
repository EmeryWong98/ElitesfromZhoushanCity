package com.dx.zjxz_gwjh.controller.management;

import com.dx.easyspringweb.api.annotation.Api;
import com.dx.easyspringweb.api.annotation.ApiModule;
import com.dx.easyspringweb.core.annotation.Action;
import com.dx.easyspringweb.core.annotation.BindResource;
import com.dx.zjxz_gwjh.dto.SummaryDto;
import com.dx.zjxz_gwjh.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiModule("Index")
@Api(name = "IndexManagement", description = "首页")
@RestController
@RequestMapping("/management/index")
@BindResource(value = "index:management")
public class IndexManagement {
    @Autowired
    private IndexService indexService;

    @BindResource(value = "index:management:totals")
    @Action(value = "统计总数", type = Action.ActionType.QUERY_LIST)
    @PostMapping("/totals")
    public SummaryDto getTotals() {
        SummaryDto summary = new SummaryDto();
        summary.setStudentsCount(indexService.getStudentsCount());
        summary.setUniversityCount(indexService.getUniversityCount());
        summary.setHighSchoolCount(indexService.getHighSchoolCount());
        summary.setNetCount(indexService.getNetCount());
        return summary;
    }

}
