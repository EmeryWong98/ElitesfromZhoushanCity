package com.dx.zjxz_gwjh.filter;

import com.dx.easyspringweb.api.annotation.ApiModel;
import com.dx.easyspringweb.api.annotation.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("高中筛选条件")
public class HighSchoolFilter {
    @ApiModelProperty("关键词")
    private String keyword;

    @ApiModelProperty("名称")
    private String name;
}
