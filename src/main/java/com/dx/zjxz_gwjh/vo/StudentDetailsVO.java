package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("学子详情VO")
public class StudentDetailsVO {
    @ApiModelProperty("学子ID")
    private String id;

    @ApiModelProperty("学年")
    private Integer academicYear;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("生日")
    private Date dob;

    @ApiModelProperty("属地")
    private String area;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("是否重点学子")
    private Boolean isKeyContact;

    @ApiModelProperty("大学名称")
    private String universityName;

    @ApiModelProperty("专业")
    private String major;
}
