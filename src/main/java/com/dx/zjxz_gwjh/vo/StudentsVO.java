package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import com.dx.zjxz_gwjh.entity.StudentsEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("学子VO")
public class StudentsVO {
    @ApiModelProperty("学子ID")
    private String id;

    @ApiModelProperty("学年")
    private Integer academicYear;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("生日")
    private Date dob;

    @ApiModelProperty("大学名称")
    private String universityName;

    @ApiModelProperty("大学省份")
    private String universityProvince;

    @ApiModelProperty("最高学历")
    private String degree;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("是否重点学子")
    private Boolean isKeyContact;

    @ApiModelProperty("毕业高中")
    private String highSchoolName;

    @ApiModelProperty("是否回舟")
    private Boolean isBack;

}
