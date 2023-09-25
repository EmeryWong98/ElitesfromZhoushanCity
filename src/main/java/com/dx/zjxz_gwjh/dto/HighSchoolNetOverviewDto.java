package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("高中网格概况Dto")
public class HighSchoolNetOverviewDto {
    private String highSchoolId;
    private String highSchoolName;
    private long netCount;
    private long studentCount;
    private long eliteStudentCount;

    public HighSchoolNetOverviewDto(String highSchoolName, String highSchoolId, long netCount, long studentCount, long eliteStudentCount) {
        this.highSchoolId = highSchoolId;
        this.highSchoolName = highSchoolName;
        this.netCount = netCount;
        this.studentCount = studentCount;
        this.eliteStudentCount = eliteStudentCount;
    }
}

