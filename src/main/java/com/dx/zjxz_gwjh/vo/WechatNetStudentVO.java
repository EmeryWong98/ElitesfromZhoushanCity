package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
public class WechatNetStudentVO {

    @ApiModelProperty("学子ID")
    public String id;

    @ApiModelProperty("姓名")
    public String name;

    @ApiModelProperty("大学名称")
    public String universityName;

    @ApiModelProperty("学年")
    public Integer academicYear;

    @ApiModelProperty("是否回舟")
    public Boolean isBack;
}
