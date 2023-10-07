package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@ApiModel("学子批量上传")
@EqualsAndHashCode(callSuper = false)
public class StudentsImportDto {

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
    private int academicYear;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;

    @ApiModelProperty("回舟时间")
    private Date backTime;

    @ApiModelProperty("性别")
    private String sex;

    @NotNull(message = "身份证号不能为空")
    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("生日")
    private Date dob;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("学历")
    private String degree;

    @ApiModelProperty("家庭联系人")
    private String familyContactor;

    @ApiModelProperty("家庭联系人电话")
    private String familyContactorMobile;

    @ApiModelProperty("所属毕业班")
    private String undergraduateClass;

    @ApiModelProperty("联系老师")
    private String headTeacher;

    @ApiModelProperty("联系老师电话")
    private String headTeacherMobile;

    @ApiModelProperty("大学名称")
    private String universityName;

    @ApiModelProperty("大学省份")
    private String universityProvince;

    @ApiModelProperty("高中名称")
    private String highSchoolName;

    @ApiModelProperty("是否双一流")
    private Boolean isSupreme;

    @ApiModelProperty("高中网格名称")
    private String highSchoolNetName;

    @ApiModelProperty("高中网格联系人")
    private String highSchoolNetContactor;

    @ApiModelProperty("高中网格位置")
    private String highSchoolNetLocation;

    @ApiModelProperty("高中网格区域编码")
    private String highSchoolNetAreaCode;

    @ApiModelProperty("高中网格联系人电话")
    private String highSchoolNetContactorMobile;

    @ApiModelProperty("地区网格名称")
    private String areaNetName;

    @ApiModelProperty("地区网格联系人")
    private String areaNetContactor;

    @ApiModelProperty("地区网格位置")
    private String areaNetLocation;

    @ApiModelProperty("地区网格区域编码")
    private String areaNetAreaCode;

    @ApiModelProperty("地区网格联系人电话")
    private String areaNetContactorMobile;

    @ApiModelProperty("党政领导名字")
    private String OfficerNetName;

    @ApiModelProperty("党政领导职位")
    private String OfficerNetPosition;

    @ApiModelProperty("学联网格名称")
    private String UnionNetName;

    @ApiModelProperty("学联网格联系人")
    private String UnionNetContactor;

    @ApiModelProperty("学联网格位置")
    private String UnionNetLocation;

    @ApiModelProperty("学联网格联系人电话")
    private String UnionNetContactorMobile;
}
