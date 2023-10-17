package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("家事难事")
@EqualsAndHashCode(callSuper = false)
public class DomesticAssistanceDto extends BaseEventDto{

    @ApiModelProperty("学生ID")
    private String studentId;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("学生电话")
    private String phone;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("家事难事")
    private String content;

    @ApiModelProperty("状态")
    private Status status;

    @ApiModelProperty("备注")
    private String remark;
}
