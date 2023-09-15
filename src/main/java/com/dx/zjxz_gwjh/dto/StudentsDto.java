package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("学子")
@EqualsAndHashCode(callSuper = false)
public class StudentsDto extends BaseEventDto {

    @NotNull(message = "学生姓名不能为空")
    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("是否重点学子")
    private Boolean isKeyContact;

    @ApiModelProperty("学年")
    private String academicYear;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;

    @ApiModelProperty("回舟时间")
    private Date backTime;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("大学名称")
    private String universityName;  // 这里使用大学名称而不是ID

    @ApiModelProperty("高中名称")
    private String highSchoolName;  // 这里使用高中名称而不是ID
}
