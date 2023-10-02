package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("家庭属地网格概况Dto")
public class AreaNetOverviewDto {
    private String areaId;
    private String areaName;
    private long netCount;
    private long studentCount;
    private long eliteStudentCount;

    public AreaNetOverviewDto(String areaName, String areaId, long netCount, long studentCount, long eliteStudentCount) {
        this.areaId = areaId;
        this.areaName = areaName;
        this.netCount = netCount;
        this.studentCount = studentCount;
        this.eliteStudentCount = eliteStudentCount;
    }
}
