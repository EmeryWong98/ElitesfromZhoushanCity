package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.ZLBStatus;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel("浙里办学生详情DTO")
public class ZLBStudentDetailsDto {
    @ApiModelProperty("学生id")
    private String id;

    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("生日")
    private Date dob;

    @ApiModelProperty("高中")
    private String highSchool;

    @ApiModelProperty("高中Id")
    private String highSchoolId;


    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("学年")
    private int academicYear;

    @ApiModelProperty("审核状态")
    private ZLBStatus status;

    @ApiModelProperty("审核意见")
    private String auditOpinion;


    private String university1Name;
    private String degree1;
    private String major1;
    private String university1Province;

    private String university2Name;
    private String degree2;
    private String major2;
    private String university2Province;

    private String university3Name;
    private String degree3;
    private String major3;
    private String university3Province;

}
