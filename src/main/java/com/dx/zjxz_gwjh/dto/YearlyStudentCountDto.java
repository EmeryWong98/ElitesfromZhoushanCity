package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import lombok.Data;

@Data
@ApiModel("学年总计DTO")
public class YearlyStudentCountDto {
    private int year;
    private int count;
}
