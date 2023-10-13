package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.DegreeType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("浙里办学子")
@EqualsAndHashCode(callSuper = false)
public class ZLBStudentsDto extends BaseEventDto {
    @ApiModelProperty("学生姓名")
    @NotNull(message = "学生姓名不能为空")
    private String name;

    @ApiModelProperty("身份证号")
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("高中")
    private String highSchool;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("学年")
    private int academicYear;

    @ApiModelProperty("学历")
    private String degree;

    @ApiModelProperty("大学名称")
    private String university;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("学校省份")
    private String universityProvince;

    @ApiModelProperty("住址")
    private String address;


}
