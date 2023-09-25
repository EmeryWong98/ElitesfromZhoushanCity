package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.HighSchoolEntity;
import com.dx.zjxz_gwjh.entity.UniversityEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    private int academicYear;

    @ApiModelProperty("住址")
    private String address;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;

    @ApiModelProperty("回舟时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @ApiModelProperty("地区网格名称")
    private String areaNetName;

    @ApiModelProperty("党政领导名字")
    private String OfficerNetName;

    @ApiModelProperty("学联网格名称")
    private String UnionNetName;
}
