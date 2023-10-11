package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("回舟学子统计DTO")
public class StudentBackCountDto {
    @ApiModelProperty("回舟学子总数")
    private int count;

    @ApiModelProperty("回舟学子全日制硕士")
    private int sCount;

    @ApiModelProperty("回舟学子全日制博士")
    private int bCount;
}
