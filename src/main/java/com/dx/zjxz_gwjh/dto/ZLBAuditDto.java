package com.dx.zjxz_gwjh.dto;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.enums.ZLBStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("浙里办学子审核Dto")
@EqualsAndHashCode(callSuper = false)
public class ZLBAuditDto {

    @NotNull(message = "学生姓名不能为空")
    @ApiModelProperty("学生姓名")
    private String name;

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

    @ApiModelProperty("大学1名称")
    private String university1Name;

    @ApiModelProperty("大学1省份")
    private String university1Province;

    @ApiModelProperty("学历1")
    private String degree1;

    @ApiModelProperty("专业1")
    private String major1;

    @ApiModelProperty("大学2名称")
    private String university2Name;

    @ApiModelProperty("大学2省份")
    private String university2Province;

    @ApiModelProperty("学历2")
    private String degree2;

    @ApiModelProperty("专业2")
    private String major2;

    @ApiModelProperty("大学3名称")
    private String university3Name;

    @ApiModelProperty("大学3省份")
    private String university3Province;

    @ApiModelProperty("学历3")
    private String degree3;

    @ApiModelProperty("专业3")
    private String major3;

    @ApiModelProperty("高中ID")
    private String highSchoolId;

    @ApiModelProperty("是否双一流")
    private Boolean isSupreme;

    @ApiModelProperty("高中网格ID")
    private String highSchoolNetId;

    @ApiModelProperty("地区网格ID")
    private String areaNetId;

    @ApiModelProperty("党政领导ID")
    private String OfficerNetId;

    @ApiModelProperty("学联网格ID")
    private String UnionNetId;

    @ApiModelProperty("审核状态")
    private ZLBStatus status;

    @ApiModelProperty("审核意见")
    private String auditOpinion;

}
