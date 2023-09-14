package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("学生筛选条件")
public class StudentsFilter {
    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("高中")
    private String highSchool;

    @ApiModelProperty("大学")
    private String university;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("学年")
    private String academicYear;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("是否重点学子")
    private Boolean isKeyContact;

}
