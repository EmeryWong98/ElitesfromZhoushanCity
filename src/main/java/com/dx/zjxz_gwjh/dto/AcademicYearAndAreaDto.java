package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("毕业年份和属地DTO")
public class AcademicYearAndAreaDto {
    private int startYear;
    private int endYear;
    private String areaId; // 属地ID
}
