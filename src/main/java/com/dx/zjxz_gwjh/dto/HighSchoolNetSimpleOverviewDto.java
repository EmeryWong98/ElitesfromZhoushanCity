package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("高中网格精简概况Dto")
public class HighSchoolNetSimpleOverviewDto {
    private String highSchoolId; // 中学ID
    private String highSchoolName; // 中学名称
    private long netCount; // 网格数量
    private long studentCount; // 学子数量
}

