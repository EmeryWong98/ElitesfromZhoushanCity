package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import lombok.Data;

@Data
@ApiModel("学子详情VO")
public class StudentsDetailsVO extends StudentsEntity {
    @ApiModelProperty("大学名称")
    private String universityName;

    @ApiModelProperty("大学省份")
    private String universityProvince;

    @ApiModelProperty("最高学历")
    private String degree;

    @ApiModelProperty("专业")
    private String major;
}
