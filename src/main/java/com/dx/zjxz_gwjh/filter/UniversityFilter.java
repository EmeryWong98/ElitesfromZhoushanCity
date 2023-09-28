package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("大学筛选条件")
public class UniversityFilter {
    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("省份")
    private String province;

    @ApiModelProperty("是否双一流")
    private Boolean isSupreme;

    @ApiModelProperty("是否重点专业")
    private Boolean isKeyMajor;

}
