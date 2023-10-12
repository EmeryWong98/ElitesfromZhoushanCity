package com.dx.zjxz_gwjh.vo;


import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.Status;
import lombok.Data;

@Data
@ApiModel("家事难事VO")
public class DomesticAssistanceVO {
    @ApiModelProperty("家事难事ID")
    private String id;

    @ApiModelProperty("学生姓名")
    private String studentName;

    @ApiModelProperty("学生电话")
    private String phone;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("申报内容")
    private String content;

    @ApiModelProperty("状态")
    private Status status;

}
