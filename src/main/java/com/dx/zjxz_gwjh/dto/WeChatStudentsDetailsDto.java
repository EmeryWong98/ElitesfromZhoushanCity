package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("微信端学生详情Dto")
@EqualsAndHashCode(callSuper = false)
public class WeChatStudentsDetailsDto {
    @ApiModelProperty("学生id")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("毕业届次")
    private Integer academicYear;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;

    @ApiModelProperty("就读大学")
    private String universityName;
}
