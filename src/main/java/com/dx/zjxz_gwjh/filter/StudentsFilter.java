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

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("是否重点学子")
    private Boolean isKeyContact;

    @ApiModelProperty("开始年份")
    private Integer startYear;

    @ApiModelProperty("结束年份")
    private Integer endYear;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("属地ID")
    private String areaId;

    @ApiModelProperty("大学省份")
    private String universityProvince;

    @ApiModelProperty("学年")
    private Integer academicYear;

    @ApiModelProperty("学历")
    private String degree;

    @ApiModelProperty("学联网格ID")
    private String unionNetId;

    @ApiModelProperty("属地网格ID")
    private String areaNetId;

    @ApiModelProperty("高校网格ID")
    private String highSchoolNetId;

    @ApiModelProperty("高中ID")
    private String highSchoolId;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;
}
