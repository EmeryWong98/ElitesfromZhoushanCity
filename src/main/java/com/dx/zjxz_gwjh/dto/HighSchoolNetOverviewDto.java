package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("高中网格概况Dto")
public class HighSchoolNetOverviewDto {
    private String highSchoolName; // 中学名称
    private Long netCount; // 网格数量
    private Long keyContactCount; // 重点联络学子数量
    private Long studentCount; // 学子数量

    public HighSchoolNetOverviewDto(String highSchoolName, long netCount, long keyContactCount, long studentCount) {
        this.highSchoolName = highSchoolName;
        this.netCount = netCount;
        this.keyContactCount = keyContactCount;
        this.studentCount = studentCount;
    }
}
