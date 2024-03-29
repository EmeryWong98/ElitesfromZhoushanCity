package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("重点学子VO")
public class ElitesVO {
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

    @ApiModelProperty("学历")
    private String degree;

    @ApiModelProperty("高中网格名称")
    private String highSchoolNetName;

    @ApiModelProperty("地区网格名称")
    private String areaNetName;

    @ApiModelProperty("党政领导名字")
    private String OfficerNetName;

    @ApiModelProperty("学联网格名称")
    private String UnionNetName;

}
