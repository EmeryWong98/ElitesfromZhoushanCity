package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("高中网格概况Dto")
public class HighSchoolNetOverviewDto {
    private String highSchoolId;
    private String highSchoolName;
    private String areaId;
    private Float lon;
    private Float lat;
    private long netCount;
    private long studentCount;
    private long eliteStudentCount;

    public HighSchoolNetOverviewDto(String highSchoolName, String highSchoolId, String areaId,long netCount, long studentCount, long eliteStudentCount, Float lon, Float lat) {
        this.highSchoolId = highSchoolId;
        this.highSchoolName = highSchoolName;
        this.areaId = areaId;
        this.lat = lat;
        this.lon = lon;
        this.netCount = netCount;
        this.studentCount = studentCount;
        this.eliteStudentCount = eliteStudentCount;
    }
}

