package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("毕业年份DTO")
public class AcademicYearDto {
    private int startYear;
    private int endYear;
}
