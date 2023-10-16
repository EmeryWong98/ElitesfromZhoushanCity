package com.dx.zjxz_gwjh.vo;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("活动网格成员VO")
public class ActivityStudentVO {

    @ApiModelProperty("成员ID")
    private String id;

    @ApiModelProperty("成员姓名")
    private String name;

    public ActivityStudentVO(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
