package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("总计")
@EqualsAndHashCode(callSuper = false)
public class SummaryDto {
    @ApiModelProperty("高中总计")
    private int highSchoolCount;
    @ApiModelProperty("学子总计")
    private int studentsCount;
    @ApiModelProperty("大学总计")
    private int universityCount;
    @ApiModelProperty("网格总计")
    private int netCount;
}
