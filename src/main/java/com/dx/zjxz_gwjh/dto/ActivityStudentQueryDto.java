package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("活动网格学子查询Dto")
public class ActivityStudentQueryDto {

    @ApiModelProperty("网格人员ID")
    private String userId;

    @ApiModelProperty("网格ID")
    private String netId;
}
